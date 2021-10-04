package ru.skillbox.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.SupportMessageRequest;

@Service
public class SupportService {
    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<?> supportMessage(SupportMessageRequest request){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(request.getEmail());
        mailMessage.setTo("bofoj50733@decorbuz.com");
        mailMessage.setSubject("SupportMessage");
        mailMessage.setText(request.getMessage());
        javaMailSender.send(mailMessage);
        return ResponseEntity.ok("ok");
    }
}
