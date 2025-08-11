package com.shubham.mongoDB.controllers;


import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.Entities.Apiresponce;
import com.shubham.mongoDB.Entities.UsernonDb;
import com.shubham.mongoDB.service.EmailService;
import com.shubham.mongoDB.service.UserEntryService;
import com.shubham.mongoDB.service.Wetherservice;
import com.shubham.mongoDB.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/public")
@Slf4j
public class publicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserEntryService serv;

    @Autowired
    private Wetherservice wserv;

    @Autowired
    private EmailService emailService;

    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            user.setDate(LocalDateTime.now());
            serv.AddUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("User creation failed: "+ e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsernonDb user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getwether")
    public ResponseEntity<?> getWether(){
        Apiresponce.Current res = wserv.getApi("Mumbai").getCurrent();
        if(res != null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/checkdb")
    public ResponseEntity<?> checkDb(){
        wserv.checkDb();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/testmail")
    public  ResponseEntity<?> testMail(){
        try{
            emailService.sendEmail("shubhamliman302001@gmail.com","Test Email", "This is a test mail");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
