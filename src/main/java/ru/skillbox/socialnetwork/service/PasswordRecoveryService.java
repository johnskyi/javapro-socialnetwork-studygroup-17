package ru.skillbox.socialnetwork.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.model.Person;
import ru.skillbox.socialnetwork.repository.PersonRepo;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoveryService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PersonRepo personRepo;

    @Value("${spring.mail.username}")
    private String userName;

    public ResponseEntity send(String email) {
        Optional<Person> personOptional = personRepo.findByEmail(email); // Проверяем есть ли пользователь с таким email
        if (personOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message.builder()
                    .error("invalid_request")
                    .errorDescription("string")
                    .build());
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userName);
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Recovery");
        mailMessage.setText("text message"); // Здесь текст сообщения, который отправляется на почту пользователя. Вопрос где взять ссылку
        javaMailSender.send(mailMessage);
        return ResponseEntity.status(HttpStatus.OK).body((Message.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(Map.of("message", "ok"))
                .build()));
    }

    public ResponseEntity setPassword(String password, String token) {
        Optional<Person> personOptional = personRepo.findByToken(token);
        if (personOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message.builder()
                    .error("invalid_request")
                    .errorDescription("string")
                    .build());
        }
        personOptional.get().setPassword(password);
        personRepo.save(personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body((Message.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(Map.of("message", "ok"))
                .build()));
    }

    @Builder
    private static class Message {
        private String error;
        private String errorDescription;
        private long timestamp;
        private Map<String, String> data;
    }

}
