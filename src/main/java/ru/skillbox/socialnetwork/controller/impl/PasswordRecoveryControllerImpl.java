package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.data.dto.PasswordRecoveryResponse;
import ru.skillbox.socialnetwork.service.impl.PasswordRecoveryServiceImpl;

import java.security.Principal;

@RestController
@Api(tags = "Работа с аккаунтом")
@Slf4j
public class PasswordRecoveryControllerImpl {

    private final PasswordRecoveryServiceImpl passwordRecoveryService;


    public PasswordRecoveryControllerImpl(PasswordRecoveryServiceImpl passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PutMapping("/api/v1/account/password/recovery")
    @ApiOperation(value="Отправка письма на почту при забытом пароле")
    public ResponseEntity<PasswordRecoveryResponse> sendEmail(@RequestParam("email") String email) {
        log.info("PUT /api/v1/account/password/recovery " + email);
        return ResponseEntity.ok(passwordRecoveryService.send(email));
    }

    @PutMapping("/api/v1/account/password/set")
    @ApiOperation(value="Смена пароля")
    public ResponseEntity<PasswordRecoveryResponse> setPassword( @RequestParam("password") String password, Principal principal) {
        log.info("PUT /api/v1/account/password/set " + principal.getName());
        return ResponseEntity.ok(passwordRecoveryService.setPassword(password, principal));
    }
    @PutMapping("/api/v1/account/email")
    @ApiOperation(value="Смена почты")
    public ResponseEntity<PasswordRecoveryResponse> setEmail(@RequestParam("email") String newEmail, Principal principal)
    {
        log.info("PUT /api/v1/account/email" + newEmail);
        return ResponseEntity.ok(passwordRecoveryService.setEmail(newEmail,principal));
    }
}
