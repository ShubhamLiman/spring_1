package com.shubham.mongoDB.repository;

import com.shubham.mongoDB.Entities.JournalEntries;
import com.shubham.mongoDB.Entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalEntryRepo extends MongoRepository<JournalEntries, ObjectId> {
}
