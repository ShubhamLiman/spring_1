package com.shubham.mongoDB.Scheduler;

import com.shubham.mongoDB.Entities.JournalEntries;
import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.enums.Sentiment;
import com.shubham.mongoDB.model.SentimentData;
import com.shubham.mongoDB.repository.UserRepoImpl;
import com.shubham.mongoDB.service.AppCache;
import com.shubham.mongoDB.service.EmailService;
import com.shubham.mongoDB.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private SentimentService sentimentService;

    @Autowired
    private AppCache appCache;

    @Autowired
    KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void fetchUsersAndMail() {
        List<User> users = userRepo.getUserforSA();

        for (User user : users) {
            List<JournalEntries> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCount = new HashMap<>();
            for(Sentiment s:sentiments){
                if(s != null){
                    sentimentCount.put(s,sentimentCount.getOrDefault(s,0)+1);
                }
            }

            Sentiment mostFreq = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFreq = entry.getKey();
                }
            }
            if(mostFreq != null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFreq).build();
//                try{
                    kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
//                }catch (Exception e){
//                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
//                }
            }


        }
    }

    @Scheduled(cron = "0 */5 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }

}
