package com.example.Uday.JournalApp.controller;

import com.example.Uday.JournalApp.Entities.JournalEntry;
import com.example.Uday.JournalApp.Entities.User;
import com.example.Uday.JournalApp.Services.JournalEntryService;
import com.example.Uday.JournalApp.Services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")


@RequestMapping("/journal")
@Tag(name="Journal Entry Controller")
public class JournalEntryController {
    @Autowired
    JournalEntryService entryService;
    @Autowired
    UserService userService;
    @GetMapping("")
    public ResponseEntity<List<JournalEntry>> getAll(){
        Authentication authenticatedObject = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authenticatedObject.getPrincipal()).getUsername();

        User user=userService.findByUserName(userName);
    try{
        List<JournalEntry> list=user.getUserEntries();
        return new ResponseEntity<>(list,HttpStatus.OK);
            }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    }
    @PostMapping()
    @Transactional
    public ResponseEntity<?> add(@RequestBody JournalEntry newEntry){
        Authentication authenticatedObject = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authenticatedObject.getPrincipal()).getUsername();
        User user=userService.findByUserName(userName);

        try{
            newEntry.setDate(LocalDateTime.now());
            user.getUserEntries().add(newEntry);

            entryService.saveEntry(newEntry);
//            return true;
            userService.saveOldUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> get(@PathVariable String myId) {  // Changed type to String
        System.out.println("Get By ID " + myId + " Is CALLED");

        Authentication authenticatedObject = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authenticatedObject.getPrincipal()).getUsername();
        User user = userService.findByUserName(userName);

        // Convert String ID to ObjectId
        ObjectId objectId;
        try {
            objectId = new ObjectId(myId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid ID format", HttpStatus.BAD_REQUEST);
        }

        Optional<JournalEntry> entry = user.getUserEntries().stream()
                .filter(ent -> ent.getId().equals(objectId))
                .findFirst();

        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?>  updateEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry updatedEntry){

        JournalEntry old=entryService.findById(myId).orElse(null);
        if(old!=null){
            old.setContent(updatedEntry!=null && !updatedEntry.getContent().equals("")? updatedEntry.getContent() : old.getContent());
            old.setTitle(updatedEntry!=null && !updatedEntry.getTitle().equals("")?updatedEntry.getTitle():old.getTitle());
            entryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable String myId) {
        System.out.println("Called delete Handler");

        // Convert the String ID to an ObjectId
        ObjectId objectId;
        try {
            objectId = new ObjectId(myId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid ObjectId format", HttpStatus.BAD_REQUEST);
        }

        Authentication authenticatedObject = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authenticatedObject.getPrincipal()).getUsername();
        User user = userService.findByUserName(userName);

        JournalEntry found = entryService.findById(objectId).orElse(null);
        if (found != null) {
            user.getUserEntries().remove(found);
            entryService.deleteEntry(objectId);
            userService.saveOldUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
