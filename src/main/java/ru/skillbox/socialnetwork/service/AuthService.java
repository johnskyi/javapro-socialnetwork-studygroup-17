package ru.skillbox.socialnetwork.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.ErrorResponse;
import ru.skillbox.socialnetwork.data.dto.LoginRequest;
import ru.skillbox.socialnetwork.data.dto.LoginResponse;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthService {
    private final PersonRepo personRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider provider;

    public AuthService(PersonRepo personRepository, AuthenticationManager authenticationManager, JwtTokenProvider provider) {
        this.personRepository = personRepository;
        this.authenticationManager = authenticationManager;
        this.provider = provider;
    }

    public ResponseEntity<?> login(LoginRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            Person person = personRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if(person.isBlocked() || !person.isApproved()){
                throw new UsernameNotFoundException("User not found");
            }
            String token = provider.creteToken(person.getEmail());
            return ResponseEntity.ok(createFullLoginResponse(person, token));

        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
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



    private LoginResponse createFullLoginResponse(Person person, String token) {
        return LoginResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(LoginResponse.Data.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .regDate(person.getRegTime().toEpochSecond(ZoneOffset.UTC))
                        .birthDate(person.getBirthTime() == null ? null : person.getBirthTime().toEpochSecond(ZoneOffset.UTC))
                        .email(person.getEmail())
                        .phone(person.getPhone() == null ? null : person.getPhone())
                        .photo(person.getPhoto() == null ? null : person.getPhoto())
                        .about(person.getAbout() == null ? null : person.getPhoto())
                        .country(person.getTown() == null ? null : person.getTown().getCountry())
                        .messagePermission(person.getMessagePermission())
                        .lastOnlineTime(person.getLastOnlineTime().toEpochSecond(ZoneOffset.UTC))
                        .isBlocked(person.isBlocked())
                        .userType(person.getType())
                        .token(token)
                        .build())
                .build();
    }
}
