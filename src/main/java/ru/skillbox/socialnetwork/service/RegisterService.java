package ru.skillbox.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.ConfirmUserRequest;
import ru.skillbox.socialnetwork.data.dto.RegisterRequest;
import ru.skillbox.socialnetwork.data.dto.RegisterResponse;
import ru.skillbox.socialnetwork.data.entity.NotificationSettings;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.UserType;
import ru.skillbox.socialnetwork.data.repository.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.exception.PasswordsNotEqualsException;
import ru.skillbox.socialnetwork.exception.PersonAlReadyRegisterException;
import ru.skillbox.socialnetwork.exception.PersonNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final PersonRepo personRepo;

    private final JavaMailSender javaMailSender;

    private final NotificationSettingsRepository notificationSettingsRepository;

    @Value("${spring.mail.username}")
    private String userName;

    public RegisterResponse regPerson(RegisterRequest registerRequest) throws PersonAlReadyRegisterException, PasswordsNotEqualsException {
        Person person = createPerson(registerRequest);
        personRepo.save(person);
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setPerson(person);
        notificationSettingsRepository.save(notificationSettings);
        sendConfirmMessage(person.getEmail(), String.valueOf(person.getId()));
        return RegisterResponse.builder()
                .timestamp(LocalDateTime.now())
                .data(RegisterResponse.Data.builder()
                        .message("ok")
                        .build())
                .build();
    }

    public RegisterResponse confirmPerson(ConfirmUserRequest confirmUserRequest) {
        Person person = findPersonById(confirmUserRequest);
        person.setApproved(true);
        personRepo.save(person);
        return RegisterResponse.builder()
                .timestamp(LocalDateTime.now())
                .data(RegisterResponse.Data.builder()
                        .message("ok")
                        .build())
                .build();
    }

    private void sendConfirmMessage(String email, String personId) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userName);
        mailMessage.setTo(email);
        mailMessage.setSubject("Подтверждение учетной записи");
        mailMessage.setText("Для подтверждение учетной записи, пожалуйста, пройдите по ссылке \n" +
                "http://45.134.255.54:5000/registration/complete?userId=" + personId);
        javaMailSender.send(mailMessage);
    }

    private Person createPerson(RegisterRequest registerRequest) throws PersonAlReadyRegisterException, PasswordsNotEqualsException {
        checkEqualsPasswords(registerRequest);
        checkPersonIsPresent(registerRequest);
        Person person = new Person();
            person.setFirstName(registerRequest.getFirstName());
            person.setLastName(registerRequest.getLastName());
            person.setEmail(registerRequest.getEmail());
            person.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPasswd1()));
            person.setRegTime(LocalDateTime.now());
            person.setLastOnlineTime(LocalDateTime.now());
            person.setType(UserType.USER);
            person.setGender("male");
        return person;
    }

    private Boolean checkPersonIsPresent(RegisterRequest request) {
        if (personRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new PersonAlReadyRegisterException("Person already register");
        } else {
            return true;
        }
    }

    private Boolean checkEqualsPasswords(RegisterRequest registerRequest) throws PasswordsNotEqualsException {
        if (registerRequest.getPasswd1().equals(registerRequest.getPasswd2())) {
            return true;
        } else {
            throw new PasswordsNotEqualsException("Passwords not Equals");
        }
    }

    private Person findPersonById(ConfirmUserRequest request) {
        return personRepo.findById(request.getUserId()).orElseThrow(() -> new PersonNotFoundException("Person not Found"));
    }
}
