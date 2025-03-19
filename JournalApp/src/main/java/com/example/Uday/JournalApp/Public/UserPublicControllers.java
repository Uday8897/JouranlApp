package com.example.Uday.JournalApp.Public;

import com.example.Uday.JournalApp.DTOS.LogInResponseDTO;
import com.example.Uday.JournalApp.Entities.User;
import com.example.Uday.JournalApp.Repository.UserRepository;
import com.example.Uday.JournalApp.Services.JWTService;
import com.example.Uday.JournalApp.Services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name="Public User Controller")
public class UserPublicControllers {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            return new ResponseEntity<>(userService.verify(user), HttpStatus.OK);

        }catch (Exception e){

            log.error("Error occured During logg in In "+user.getUserName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }
    @PostMapping("/signup")
    public User signUp(@RequestBody User user){
        userService.saveUser(user);
        return user;
    }
}
