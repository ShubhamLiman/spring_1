package com.shubham.mongoDB;

import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.Scheduler.UserScheduler;
import com.shubham.mongoDB.repository.UserRepoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class userSATest {

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void testSentmentAnalysis(){
        List<User> use = userRepo.getUserforSA();
        System.out.println(use);
    }

    @Test
    public void testSendUserSentimentMail(){
        userScheduler.fetchUsersAndMail();
    }

}
