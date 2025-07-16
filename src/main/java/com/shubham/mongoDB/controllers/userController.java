package com.shubham.mongoDB.controllers;

import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.Entities.UsernonDb;
import com.shubham.mongoDB.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserEntryService serv;

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user){
//        try{
//            user.setDate(LocalDateTime.now());
//            serv.AddUser(user);
//            return new ResponseEntity<>(user,HttpStatus.CREATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }



    @GetMapping("id/{myId}")
    public ResponseEntity<User> getuserbyID(@PathVariable ObjectId myId){
        Optional<User> user = serv.getUserByID(myId);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            serv.deleteByname(auth.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/updateuser")
    public ResponseEntity<User> updateUserById(@RequestBody UsernonDb updateUser){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User old = serv.findbyName(username);
            if(old != null){
                old.setName(updateUser.getName() != null && !updateUser.getName().equals("") ? updateUser.getName() : old.getName());
                old.setEmail(updateUser.getEmail() != null && !updateUser.getEmail().equals("") ? updateUser.getEmail() : old.getEmail());
                old.setPassword(updateUser.getPassword() != null && !updateUser.getPassword().equals("") ? updateUser.getPassword() : old.getPassword());
                serv.UpdateUser(old);
            }
            return new ResponseEntity<>(old,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
