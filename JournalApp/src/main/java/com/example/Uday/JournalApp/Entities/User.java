package com.example.Uday.JournalApp.Entities;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "Users")
@Data
public class User {
    @Id
    ObjectId userId;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String passWord;
    private String email;
    private boolean sentimentAnalysis;
    @DBRef
    List<JournalEntry> userEntries=new ArrayList<>();
    List<String> roles=Arrays.asList("USER");


    public List<String> getJournalEntries() {
        return userEntries.stream().map(journalEntry -> journalEntry.getContent()).collect(Collectors.toList());
    }
}
