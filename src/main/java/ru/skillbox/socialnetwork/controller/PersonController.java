package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.dto.RegisterRequest;
import ru.skillbox.socialnetwork.data.dto.RegisterResponse;

import java.security.Principal;

public interface PersonController {
    ResponseEntity<?> getPersonDetail(Principal principal);

    ResponseEntity<PersonResponse> putPersonDetail(PersonRequest request, Principal principal);

    ResponseEntity<PersonResponse> deletePerson(Principal principal);

}
