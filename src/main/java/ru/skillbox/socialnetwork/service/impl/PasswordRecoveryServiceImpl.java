package ru.skillbox.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.PasswordRecoveryResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;

import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoveryServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PersonRepo personRepo;

    @Value("${spring.mail.username}")
    private String userName;

    public PasswordRecoveryResponse send(String email) {
        findPersonByEmail(email);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userName);
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Recovery");
        mailMessage.setText("Для смены пароля пожалуйста пройдите по ссылке \n" +
                "");
        javaMailSender.send(mailMessage);
        return PasswordRecoveryResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(Map.of("message", "ok"))
                .build();
    }

    public PasswordRecoveryResponse setPassword(String password, String token) {
        Person person = findPersonByCode(token);
        person.setPassword(new BCryptPasswordEncoder(12).encode(password));
        personRepo.save(person);
        return PasswordRecoveryResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(Map.of("message", "ok"))
                .build();
    }


    private Person findPersonByEmail(String email) {
        return personRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private Person findPersonByCode(String token) {
        return personRepo.findByCode(token)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
