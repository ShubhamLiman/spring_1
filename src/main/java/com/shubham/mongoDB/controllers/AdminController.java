package com.shubham.mongoDB.controllers;

import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.Entities.UsernonDb;
import com.shubham.mongoDB.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserEntryService serv;

    @GetMapping("/getallusers")
    public ResponseEntity<?> getall(){
        List<User> users=  serv.getAllUsers();
        if(users != null && !users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createadmin")
    public ResponseEntity<?> createAdmin(@RequestBody UsernonDb updateUser){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User old = serv.findbyName(updateUser.getName());
            serv.saveAdmin(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
