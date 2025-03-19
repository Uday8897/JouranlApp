package com.example.Uday.JournalApp.Repository;

import com.example.Uday.JournalApp.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserRepositoryImpl {
    @Autowired
    MongoTemplate mongoTemplate;
    public List<User> getAllSA(){
        Query query=new Query();
        query.addCriteria(Criteria.where("SentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        List<User> users=mongoTemplate.find(query,User.class);
        return users;
    }
}
