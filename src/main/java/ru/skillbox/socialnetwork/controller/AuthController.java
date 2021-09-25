package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.LoginRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.service.AuthService;

import java.security.Principal;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/v1/auth/login")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8080")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @GetMapping("/api/v1/users/me")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8080")
    public ResponseEntity<?> getPersonDetail(Principal principal){
        return authService.me(principal);
    }

    @PostMapping("/api/v1/auth/logout")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8080")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> logout(){
        return authService.logout();
    }


}
