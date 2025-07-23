package com.shubham.mongoDB.Entities;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
@Data
public class User {
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    @Indexed(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntries> journalEntries = new ArrayList<>();
    private List<String> roles;
    private boolean sentimentAnalysis = true;
    private LocalDateTime date;

}
