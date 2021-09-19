package ru.skillbox.socialnetwork.controllers;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetwork.model.dto.PersonRequest;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;
import ru.skillbox.socialnetwork.model.dto.ResidencyRequest;
import ru.skillbox.socialnetwork.model.dto.ResidencyResponse;

import java.security.Principal;

public interface PersonController {
    ResponseEntity<PersonResponse> getPersonDetail(Principal principal);

    ResponseEntity<ResidencyResponse> getPersonResidency(ResidencyRequest request);

    ResponseEntity<PersonResponse> putPersonDetail(PersonRequest request, Principal principal);

    ResponseEntity<PersonResponse> deletePerson(Principal principal);
}
