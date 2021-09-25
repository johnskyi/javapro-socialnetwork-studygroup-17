package ru.skillbox.socialnetwork.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.ErrorResponse;
import ru.skillbox.socialnetwork.data.dto.LoginRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthService {
    private final PersonRepo personRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(PersonRepo personRepository, AuthenticationManager authenticationManager) {
        this.personRepository = personRepository;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<?> login(LoginRequest request){
        Person person = personRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if(!encoder.matches(request.getPassword(), person.getPassword())){
            return new ResponseEntity<Object>(new ErrorResponse("invalid_request", "invalid_password"),
                    HttpStatus.BAD_REQUEST);
        }
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(createFullPersonResponse(person));

    }

    public ResponseEntity<?> me(Principal principal){
        if(SecurityContextHolder.getContext().getAuthentication().getName() == null){
            return new ResponseEntity<>(new ErrorResponse("invalid_request", "User unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(createFullPersonResponse(personRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
        ), HttpStatus.OK);
    }

    public ResponseEntity<?> logout(){
        SecurityContextHolder.clearContext();
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            return new ResponseEntity<>(new ErrorResponse("invalid_request", "Error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(PersonResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(PersonResponse.Data.builder().message("ok").build()).build(),
                HttpStatus.OK);
    }



    private PersonResponse createFullPersonResponse(Person person) {
        String[] location = person.getTown().split(",");
        return PersonResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(PersonResponse.Data.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .regDate(person.getRegTime())
                        .birthDate(person.getBirthTime())
                        .email(person.getEmail())
                        .phone(person.getPhone())
                        .photo(person.getPhoto())
                        .about(person.getAbout())
                        .country(location[0].trim())
                        .city(location[1].trim())
                        .messagePermission(person.getMessagePermission())
                        .lastOnlineTime(person.getLastOnlineTime())
                        .isBlocked(person.isBlocked())
                        .build())
                .build();
    }
}
