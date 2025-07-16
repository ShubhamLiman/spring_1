package com.shubham.mongoDB.repository;

import com.shubham.mongoDB.Entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepo extends MongoRepository<User, ObjectId> {

    User findByName(String userName);
    void deleteByName(String userName);
}
