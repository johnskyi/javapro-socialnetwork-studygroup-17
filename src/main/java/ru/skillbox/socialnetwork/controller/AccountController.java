package ru.skillbox.socialnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.*;
import ru.skillbox.socialnetwork.service.RegisterService;

@RestController
public class AccountController {

    @Autowired
    private final RegisterService registerService;

    public AccountController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register")
    public ResponseEntity regPerson(@RequestBody RegisterRequest registerRequest) {
        return registerService.regPerson(registerRequest);
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register/confirm")
    public ResponseEntity confirmPerson(@RequestBody ConfirmUserRequest confirmUserRequest) {
        return registerService.confirmPerson(confirmUserRequest);
    }

}
