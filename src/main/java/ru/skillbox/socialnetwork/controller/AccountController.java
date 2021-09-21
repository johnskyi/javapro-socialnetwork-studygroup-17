package ru.skillbox.socialnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.service.PasswordRecoveryService;

@RestController
public class AccountController {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @PutMapping("/api/v1/account/password/recovery")
    public void sendEmail(@RequestParam("email") String email)
    {
        passwordRecoveryService.send(email);
    }
}
