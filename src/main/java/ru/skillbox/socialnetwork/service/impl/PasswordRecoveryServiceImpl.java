package ru.skillbox.socialnetwork.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.PasswordRecoveryResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;

import java.security.Principal;
import java.util.Map;

@Service
public class PasswordRecoveryServiceImpl {

    private final JavaMailSender javaMailSender;


    private final PersonRepo personRepo;

    @Value("${spring.mail.username}")
    private String userName;

    public PasswordRecoveryServiceImpl(JavaMailSender javaMailSender, PersonRepo personRepo) {
        this.javaMailSender = javaMailSender;
        this.personRepo = personRepo;
    }

    public PasswordRecoveryResponse send(String email) {
        Person person = findPersonByEmail(email);
        String token = person.getCode();
        sendEmail(email, token);
        return PasswordRecoveryResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(Map.of("message", "ok"))
                .build();
    }

    public PasswordRecoveryResponse setPassword(String password, Principal principal) {
        Person person = findPersonByEmail(principal.getName());
        person.setPassword(new BCryptPasswordEncoder(12).encode(password));
        personRepo.save(person);
        return PasswordRecoveryResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(Map.of("message", "ok"))
                .build();
    }

    public PasswordRecoveryResponse setEmail(String newEmail, Principal principal) {
        String email = principal.getName();
        Person person = findPersonByEmail(email);
        person.setEmail(newEmail);
        personRepo.save(person);
        return PasswordRecoveryResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(Map.of("message", "ok"))
                .build();
    }


    private Person findPersonByEmail(String email) {
        return personRepo.findByEmail(email)
                .orElseThrow(() -> new PersonNotAuthorized("Sorry you are not authorized"));
    }

    private void sendEmail(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userName);
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Recovery");
        mailMessage.setText("Для смены пароля пожалуйста пройдите по ссылке \n" +
                "http://45.134.255.54:5000/change-password?token=" + token);
        javaMailSender.send(mailMessage);
    }

}
