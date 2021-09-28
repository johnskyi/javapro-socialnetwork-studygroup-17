package ru.skillbox.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.*;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.UserType;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@Tag(name = "Регистрация пользователя")
public class AccountController {

    @Autowired
    private final PersonRepo personRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String userName;

    public AccountController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register")
    @Operation(summary = "Регистрация")
    public ResponseEntity regPerson(@RequestBody RegisterRequest registerRequest) {
        if(!registerRequest.getPasswd1().equals(registerRequest.getPasswd2())){
            return new ResponseEntity<>(new ErrorResponse("invalid_request", "Пароли не совпадают"),
                    HttpStatus.BAD_REQUEST);
        }
        String email = registerRequest.getEmail();
        Optional<Person> personOptional = personRepo.findByEmail(email);
        if (!personOptional.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("invalid_request", "Пользователь уже существует"),
                    HttpStatus.BAD_REQUEST);
        }
        Person person = new Person();
        person.setFirstName(registerRequest.getFirstName());
        person.setLastName(registerRequest.getLastName());
        person.setEmail(registerRequest.getEmail());
        person.setCode(Integer.toString(new Random().nextInt(9999 - 1000) + 1000));
        person.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPasswd1()));
        person.setRegTime(LocalDateTime.now());
        person.setLastOnlineTime(LocalDateTime.now());
        person.setType(UserType.USER);
        personRepo.save(person);
        RegisterResponse registerResponse = new RegisterResponse("",LocalDateTime.now(),new RegisterResponse.Data("ok"));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userName);
        mailMessage.setTo(email);
        mailMessage.setSubject("Подтверждение учетной записи");
        mailMessage.setText("Для подтверждение учетной записи, пожалуйста, пройдите по ссылке \n" +
                "http://45.134.255.54:5000/registration/complete?userId="+ person.getId()+"&token=" + person.getCode());
        javaMailSender.send(mailMessage);
        return new ResponseEntity<>(registerResponse,HttpStatus.OK);
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register/confirm")
    @Operation(summary = "Подтверждение регистрации")
    public ResponseEntity confirmPerson(@RequestBody ConfirmUserRequest confirmUserRequest) {
        Person person = personRepo.findByIdAndCode(confirmUserRequest.getUserId(), confirmUserRequest.getToken()).get();
        if(person != null){
            person.setApproved(true);
            personRepo.save(person);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
