package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;

import java.security.Principal;

public interface PersonController {
    ResponseEntity<PersonResponse> getPersonDetail(Principal principal);

    ResponseEntity<PersonResponse> putPersonDetail(PersonRequest request, Principal principal);

    ResponseEntity<PersonResponse> deletePerson(Principal principal);
}
