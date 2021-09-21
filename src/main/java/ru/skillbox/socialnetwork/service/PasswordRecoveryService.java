package ru.skillbox.socialnetwork.service;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.model.User;
import ru.skillbox.socialnetwork.repository.UserRepo;

import java.util.Map;
import java.util.Optional;

@Service
public class PasswordRecoveryService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepo userRepo;

    @Value("${spring.mail.username}")
    private String userName;

    public ResponseEntity send(String email)
    {
        Optional<User> userOptional = userRepo.findByEmail(email); // Проверяем есть ли пользователь с таким email
        if(userOptional.isPresent()) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(userName);
            mailMessage.setTo(email);
            mailMessage.setSubject("Password Recovery");
            mailMessage.setText("text message"); // Здесь текст сообщения, который отправляется на почту пользователя. Вопрос где взять ссылку
            javaMailSender.send(mailMessage);
            return ResponseEntity.ok(Message.builder()
                    .error("string")
                    .timestamp(System.currentTimeMillis())
                    .data(Map.of("message","ok"))
                    .build());
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message.builder()
                .error("invalid_request")
                .errorDescription("string")
                .build());
    }
    @Builder
    private static class Message
    {
        private String error;
        private String errorDescription;
        private long timestamp;
        private Map<String,String> data;
    }

}
