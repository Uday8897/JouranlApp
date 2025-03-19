package com.example.Uday.JournalApp.Services;

import com.example.Uday.JournalApp.Entities.User;
import com.example.Uday.JournalApp.Repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MailService {
@Autowired
    private  JavaMailSender sender;
    @Autowired
    private  UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private  GeminiService geminiService;
    private  ExecutorService executorService=Executors.newFixedThreadPool(5);

    @Scheduled(cron = "0 0 0 * * SUN")
    public void scheduleMailTask() {
        List<User> users = userRepositoryImpl.getAllSA();

        List<User> validUsers = users.stream()
                .filter(user -> user.getEmail() != null && !user.getEmail().isEmpty())
                .collect(Collectors.toList());

        log.info("Found {} users with valid emails, sending sentiment reports...", validUsers.size());

        validUsers.forEach(user -> executorService.submit(() -> sendPersonalizedSentimentReport(user)));
    }

    public String  sendPersonalizedSentimentReport(User user) {
        try {
            if (user.getJournalEntries() == null || user.getJournalEntries().isEmpty()) {
                log.warn("No journal entries found for user: {}", user.getUserName());
                return null;
            }

            String sentimentReport = geminiService.analyzeSentiment(user.getJournalEntries());

            String emailContent = String.format(
                    "Hello %s,\n\nHere is your weekly sentiment analysis based on your journal entries:\n\n%s\n\nBest regards,\nUday Journal App Team",
                    user.getUserName(), sentimentReport
            );

            sendEmail(user.getEmail(), "Your Weekly Sentiment Report", emailContent);
            return emailContent;
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", user.getEmail(), e.getMessage(), e);
            return null;
        }
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            sender.send(message);
            log.info("Sent sentiment report to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
        }
    }

    public void shutdown() {
        log.info("Shutting down MailService ExecutorService...");
        executorService.shutdown();
    }
}
