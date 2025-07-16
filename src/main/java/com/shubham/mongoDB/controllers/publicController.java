package com.shubham.mongoDB.controllers;


import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private UserEntryService serv;

    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            user.setDate(LocalDateTime.now());
            serv.AddUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
