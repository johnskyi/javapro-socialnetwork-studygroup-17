package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.LoginRequest;
import ru.skillbox.socialnetwork.service.AuthService;

@RestController
@Api(tags = "Авторизация")
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/v1/auth/login")
    @ApiOperation(value="Авторизация")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        log.info("Авторизовался пользователь: " + request.getEmail());
        return authService.login(request);
    }

    @PostMapping("/api/v1/auth/logout")
    @ApiOperation(value="Выполнить выход")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> logout(){
        return authService.logout();
    }


}
