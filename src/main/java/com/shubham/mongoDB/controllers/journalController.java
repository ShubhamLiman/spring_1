package com.shubham.mongoDB.controllers;

import com.shubham.mongoDB.Entities.JournalEntries;
import com.shubham.mongoDB.Entities.JournalNondb;
import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.service.JournalEntryService;
import com.shubham.mongoDB.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalController {
    @Autowired
    private JournalEntryService journalserv;

    @Autowired
    private UserEntryService userserv;

    @GetMapping("/getallofuser")
    public ResponseEntity<?> getAllEntriesofUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userserv.findbyName(userName);
        List<JournalEntries> entries = user.getJournalEntries();
        if(entries != null && !entries.isEmpty()){
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getbyid/{Id}")
    public ResponseEntity<?> getjournalEntrybyId(@PathVariable ObjectId Id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userserv.findbyName(userName);
        List<JournalEntries> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(Id)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntries> entry = journalserv.getEntryByID(Id);
            if (entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createnewentry")
    public ResponseEntity<JournalEntries> createEntry(@RequestBody JournalEntries newEntry){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            journalserv.saveEntry(newEntry,userName);
            return new ResponseEntity<>(newEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId myId){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            journalserv.deleteEntryById(myId,userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{Id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId Id, @RequestBody JournalNondb updateEntry){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userserv.findbyName(userName);
        List<JournalEntries> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(Id)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntries> entry = journalserv.getEntryByID(Id);
            if (entry.isPresent()) {
                JournalEntries old = entry.get();
                old.setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().equals("") ? updateEntry.getTitle():old.getTitle());
                old.setContent(updateEntry.getContent() != null && !updateEntry.getContent().equals("") ? updateEntry.getContent():old.getContent());
                journalserv.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
