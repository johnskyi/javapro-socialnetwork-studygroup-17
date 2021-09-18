package ru.skillbox.socialnetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;
import ru.skillbox.socialnetwork.services.PersonService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class PersonControllerImpl implements PersonController {

    private final PersonService userService;

    @Override
    @GetMapping("/me")
    //   @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PersonResponse> getUserDetail(Principal principal) {
        return ResponseEntity.ok(userService.getUserDetail(principal));
    }

}
