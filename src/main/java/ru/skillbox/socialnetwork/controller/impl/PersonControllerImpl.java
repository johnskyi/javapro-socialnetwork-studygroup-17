package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.controller.PersonController;
import ru.skillbox.socialnetwork.data.dto.*;
import ru.skillbox.socialnetwork.service.PersonService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Api(tags = "Работа с пользователями")
@RequestMapping("/api/v1/users")
public class PersonControllerImpl implements PersonController {

    private final PersonService personService;
    private final Logger logger = LoggerFactory.getLogger(PersonControllerImpl.class);

    @Override
    public ResponseEntity<PersonResponse> getPersonDetail(Principal principal) {
        logger.info("Call GET /api/v1/users/me");
        return ResponseEntity.ok(personService.getPersonDetail(principal));
    }

    @Override
    public ResponseEntity<PersonResponse> putPersonDetail(@Valid @RequestBody PersonRequest personRequest, Principal principal) {
        logger.info("Call PUT /api/v1/users/me");
        return ResponseEntity.ok(personService.putPersonDetail(personRequest, principal));
    }

    @Override
    public ResponseEntity<PersonResponse> deletePerson(Principal principal) {
        logger.info("Call DELETE /api/v1/users/me");
        return ResponseEntity.ok(personService.deletePerson(principal));
    }

}
