package ru.skillbox.socialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.service.PasswordRecoveryService;

@RestController
@AllArgsConstructor
public class AccountController {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @PutMapping("/api/v1/account/password/recovery")
    public void sendEmail(@RequestParam("email") String email)
    {
        passwordRecoveryService.send(email);
    }

    @PutMapping("/api/v1/account/password/set")
    public void setPassword(@RequestParam("email") String password, @RequestParam("token") String token)
    {
        passwordRecoveryService.setPassword(password,token);
    }
}
