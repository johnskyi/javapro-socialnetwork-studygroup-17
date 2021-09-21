package ru.skillbox.socialnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.model.entities.User;
import ru.skillbox.socialnetwork.model.repositories.UserRepository;
import ru.skillbox.socialnetwork.model.request.AuthenticationRequestDTO;
import ru.skillbox.socialnetwork.security.JWTTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;


    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepository userRepository, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("api/v1/auth/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));
            User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtTokenProvider.createToken(requestDTO.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", requestDTO.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email/password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("api/v1/auth/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
