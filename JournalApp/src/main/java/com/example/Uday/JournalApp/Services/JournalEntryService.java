package com.example.Uday.JournalApp.Services;

import com.example.Uday.JournalApp.Entities.JournalEntry;
import com.example.Uday.JournalApp.Repository.JournalRepository;
import com.example.Uday.JournalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalRepository journalRepo;
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(JournalEntry myEntry) {
        journalRepo.save(myEntry);
    }
    public List<JournalEntry> getAllEntries(){
        return journalRepo.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalRepo.findById(id);
    }
    public void deleteEntry(ObjectId id){
        journalRepo.deleteById(id);
    }


}
