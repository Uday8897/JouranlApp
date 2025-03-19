package com.example.Uday.JournalApp.controller;

import com.example.Uday.JournalApp.Entities.User;
import com.example.Uday.JournalApp.Services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller" )
public class AdminController {
    @Autowired
    UserService userService;
@GetMapping("/all-users")
public  List<User> getAll(){

    return userService.findAll();
    }
}
