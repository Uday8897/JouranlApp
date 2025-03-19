package com.example.Uday.JournalApp.controller;

import com.example.Uday.JournalApp.Entities.User;
import com.example.Uday.JournalApp.Repository.UserRepository;
import com.example.Uday.JournalApp.Repository.UserRepositoryImpl;
import com.example.Uday.JournalApp.Services.MailService;
import com.example.Uday.JournalApp.Services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Slf4j
@RequestMapping("/user")
@Tag(name = "User Controller")
public class UserController {
    @Autowired
  UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/send-my-report")
    public ResponseEntity<Resource> sendMySentimentReport() {
        try {
            Authentication authenticatedObject = SecurityContextHolder.getContext().getAuthentication();
            String userName = ((UserDetails) authenticatedObject.getPrincipal()).getUsername();
            User user = userRepository.findByuserName(userName);

            // Check Redis for an existing report
            String redisKey = "sentiment_report:" + user.getUserName();
            System.out.println(redisKey);
            String cachedReport = (String) redisTemplate.opsForValue().get(redisKey);
            System.out.println(cachedReport);

            if (cachedReport != null) {
                log.info("Found cached report for {}", user.getEmail());
            } else {
                log.info("Generating new sentiment report for {}", user.getEmail());
                cachedReport = mailService.sendPersonalizedSentimentReport(user);

                // Store in Redis for a week
                redisTemplate.opsForValue().set(redisKey, cachedReport, Duration.ofDays(7));
            }


            byte[] reportBytes = cachedReport.getBytes(StandardCharsets.UTF_8);
            ByteArrayResource resource = new ByteArrayResource(reportBytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sentiment_report.txt")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(reportBytes.length)
                    .body(resource);

        } catch (Exception e) {
            log.error("Error occurred while processing sentiment report: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName,@RequestBody User updatedUser) {
        User old = userService.findByUserName(userName);
        if (old != null) {
            old.setUserName(updatedUser.getUserName());
            old.setPassWord(updatedUser.getPassWord());
            return new ResponseEntity<>(old, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
@DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId userId){
     userService.deleteUser(userId);
     return new ResponseEntity<>(HttpStatus.OK);

        }
    }

