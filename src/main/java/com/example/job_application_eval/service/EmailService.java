package com.example.job_application_eval.service;


import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String to, String subject, String text) throws MessagingException;
}
