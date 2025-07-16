package com.shubham.mongoDB.service;

import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepo repo;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void AddUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        repo.save(user);
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
