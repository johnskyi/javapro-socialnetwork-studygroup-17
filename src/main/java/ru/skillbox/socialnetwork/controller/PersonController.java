package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;

import java.security.Principal;

public interface PersonController {

    @GetMapping("/me")
    ResponseEntity<PersonResponse> getPersonDetail(Principal principal);

    @PutMapping("/me")
    ResponseEntity<PersonResponse> putPersonDetail(PersonRequest request, Principal principal);

    @DeleteMapping("/me")
    ResponseEntity<PersonResponse> deletePerson(Principal principal);

}
