package com.shubham.mongoDB.Entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("configuration_collection")
@Data
@NoArgsConstructor
public class ConfigEntity {
    @Id
    private ObjectId _id;
    private String key;
    private String value;
}
