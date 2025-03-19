package com.example.Uday.JournalApp.Repository;

import com.example.Uday.JournalApp.Entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository public interface UserRepository extends MongoRepository<User, ObjectId> {
    public User findByuserName(String userName);
}
