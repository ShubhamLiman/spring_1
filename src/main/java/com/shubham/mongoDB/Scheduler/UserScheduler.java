package com.shubham.mongoDB.Scheduler;

import com.shubham.mongoDB.service.EmailService;
import com.shubham.mongoDB.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserEntryService userEntryService;

    public void fetchUsersAndMail(){

    }

}
