package com.shubham.mongoDB.service;

import com.shubham.mongoDB.Entities.JournalEntries;
import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.repository.journalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private journalEntryRepo repo;

    @Autowired
    private UserEntryService userservice;

    public Optional<JournalEntries> getEntryByID(ObjectId id){
        return repo.findById(id);
    }



    @Transactional
    public void saveEntry(JournalEntries entry, String username){
        try {
            User user = userservice.findbyName(username);
            entry.setDate(LocalDateTime.now());
            JournalEntries saved = repo.save(entry);
            user.getJournalEntries().add(saved);
            userservice.UpdateUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(JournalEntries entry){
        repo.save(entry);
    }

    @Transactional
    public void deleteEntryById(ObjectId id, String username){
        try {
            User user = userservice.findbyName(username);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userservice.UpdateUser(user);
                repo.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
