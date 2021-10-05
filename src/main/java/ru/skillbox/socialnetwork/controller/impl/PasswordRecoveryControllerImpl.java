package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.data.dto.PasswordRecoveryResponse;
import ru.skillbox.socialnetwork.service.impl.PasswordRecoveryServiceImpl;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Api(tags = "Работа с аккаунтом")
public class PasswordRecoveryControllerImpl {

    @Autowired
    private PasswordRecoveryServiceImpl passwordRecoveryService;

    private final Logger logger = LoggerFactory.getLogger(PasswordRecoveryControllerImpl.class);

    @PutMapping("/api/v1/account/password/recovery")
    public ResponseEntity<PasswordRecoveryResponse> sendEmail(@RequestParam("email") String email) {
        logger.info("Person {} requested the password recovery", email);
        return ResponseEntity.ok(passwordRecoveryService.send(email));
    }

    @PutMapping("/api/v1/account/password/set")
    public ResponseEntity<PasswordRecoveryResponse> setPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        logger.info("Person {} requested the password change", token);
        return ResponseEntity.ok(passwordRecoveryService.setPassword(password, token));
    }
    @PutMapping("/api/v1/account/email")
    public ResponseEntity<PasswordRecoveryResponse> setEmail(@RequestBody String newEmail, Principal principal)
    {
        logger.info("Person {} requested the email change", newEmail);
        return ResponseEntity.ok(passwordRecoveryService.setEmail(newEmail,principal));
    }
}
