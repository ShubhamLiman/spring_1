package com.shubham.mongoDB.repository;

import com.shubham.mongoDB.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserforSA(){
        Criteria emailCriteria = new Criteria().andOperator(
                Criteria.where("email").exists(true),
                Criteria.where("email").regex("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        );

        Criteria saCriteria = Criteria.where("sentimentAnalysis").is(true);

        Query query = new Query().addCriteria(new Criteria().andOperator(emailCriteria, saCriteria));

        return mongoTemplate.find(query, User.class);
    }

}
