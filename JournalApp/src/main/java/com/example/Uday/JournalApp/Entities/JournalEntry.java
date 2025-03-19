package com.example.Uday.JournalApp.Entities;

import com.example.Uday.JournalApp.JournalAppApplication;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "jouranlEntries")

@Data
public class JournalEntry {
    @Id
    @JsonSerialize(using = JournalAppApplication.ObjectIdSerializer.class)

    private ObjectId id;
    private String title;


    private  LocalDateTime date;
    private String content;
    }



