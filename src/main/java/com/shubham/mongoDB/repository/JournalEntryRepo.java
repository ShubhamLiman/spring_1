package com.shubham.mongoDB.repository;

import com.shubham.mongoDB.Entities.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalEntries, ObjectId> {
}
