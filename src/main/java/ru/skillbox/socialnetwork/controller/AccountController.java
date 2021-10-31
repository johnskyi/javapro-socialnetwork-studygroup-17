package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.*;
import ru.skillbox.socialnetwork.service.RegisterService;

@RestController
@Api(tags = "Работа с аккаунтом")
@Slf4j
public class AccountController {

    @Autowired
    private final RegisterService registerService;

    public AccountController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register")
    @ApiOperation(value="Регистрация пользователя")
    public ResponseEntity regPerson(@RequestBody RegisterRequest registerRequest) {
        log.info("Зарегестрировался пользователь: " + registerRequest.getEmail());
        return registerService.regPerson(registerRequest);
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register/confirm")
    @ApiOperation(value="Подтверждение регистрации")
    public ResponseEntity confirmPerson(@RequestBody ConfirmUserRequest confirmUserRequest) {
        return registerService.confirmPerson(confirmUserRequest);
    }

}
