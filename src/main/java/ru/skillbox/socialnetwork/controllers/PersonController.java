package ru.skillbox.socialnetwork.controllers;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;

import java.security.Principal;

public interface PersonController {
    ResponseEntity<PersonResponse> getUserDetail(Principal principal);
}
