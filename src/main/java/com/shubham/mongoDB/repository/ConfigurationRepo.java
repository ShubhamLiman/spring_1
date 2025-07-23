package com.shubham.mongoDB.repository;

import com.shubham.mongoDB.Entities.ConfigEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepo extends MongoRepository<ConfigEntity, ObjectId> {
}
