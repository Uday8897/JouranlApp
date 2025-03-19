package com.example.Uday.JournalApp.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class LogInResponseDTO {
    private String token;
    private List<String> role;

 }
