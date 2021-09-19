package ru.skillbox.socialnetwork.controllers;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetwork.model.dto.PersonRequest;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;

import java.security.Principal;

public interface PersonController {
    ResponseEntity<PersonResponse> getPersonDetail(Principal principal);

    ResponseEntity<PersonResponse> putPersonDetail(PersonRequest personRequest, Principal principal);

    ResponseEntity<PersonResponse> deletePerson(Principal principal);
}
