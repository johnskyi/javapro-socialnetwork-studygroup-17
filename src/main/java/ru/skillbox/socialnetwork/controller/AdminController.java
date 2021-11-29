package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Работа с админкой")
@PreAuthorize("hasAuthority('user:administration')")
@Slf4j
public class AdminController {
    @GetMapping("/api/v1/admin/getAll")
    @ApiOperation(value = "Получить список друзей")
    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    public String getAll() {
        return "Admin panel";
    }


}
