package com.shubham.mongoDB.service;

import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.repository.UserEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserEntryService {



    @Autowired
    private UserEntryRepo repo;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void AddUser(User user){
        try{
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            repo.save(user);
        } catch (Exception e) {
            log.info("error occured for: {}", user.getName());
            throw new RuntimeException(e);
        }
    }

    public void saveAdmin(User user){
        user.setRoles(Arrays.asList("USER","ADMIN"));
        repo.save(user);
    }

    public void UpdateUser(User user){
        repo.save(user);
    }

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    public Optional<User> getUserByID(ObjectId id){
        return repo.findById(id);
    }

    public void deleteByID(ObjectId id){
        repo.deleteById(id);
    }

    public void deleteByname(String name){
        repo.deleteByName(name);
    }

    public User findbyName(String name){
        return repo.findByName(name);
    }

}
