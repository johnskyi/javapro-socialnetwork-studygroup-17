package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.PersonController;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.dto.PersonSearchResponse;
import ru.skillbox.socialnetwork.service.PersonService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Api(tags = "Работа с пользователями")
@RequestMapping("/api/v1/users")
@Slf4j
public class PersonControllerImpl implements PersonController {

    private final PersonService personService;
    private final Logger logger = LoggerFactory.getLogger(PersonControllerImpl.class);

    @Override
    @ApiOperation(value = "Получение профиля пользователя")
    public ResponseEntity<PersonResponse> getPersonDetail(Principal principal) {
        logger.info("Call GET /api/v1/users/me");
        return ResponseEntity.ok(personService.getPersonDetail(principal));
    }

    @Override
    @ApiOperation(value = "Редактирование профиля пользователя")
    public ResponseEntity<PersonResponse> putPersonDetail(@Valid @RequestBody PersonRequest personRequest, Principal principal) {
        logger.info("Call PUT /api/v1/users/me");
        return ResponseEntity.ok(personService.putPersonDetail(personRequest, principal));
    }

    @Override
    @ApiOperation(value = "Удаление пользователя")
    public ResponseEntity<PersonResponse> deletePerson(Boolean isHardDelete, Principal principal) {
        logger.info("Call DELETE /api/v1/users/me");
        return ResponseEntity.ok(personService.deletePerson(isHardDelete, principal));
    }

    @Override
    public ResponseEntity<PersonResponse> getPersonById(Long id) {
        logger.info("Call GET /api/v1/users/{id}");
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @Override
    @ApiOperation(value = "Поиск пользователей")
    public ResponseEntity<PersonSearchResponse> searchPerson(@RequestParam(value = "first_name", defaultValue = "")String firstName,
                                                             @RequestParam(value = "last_name", defaultValue = "")String lastName,
                                                             @RequestParam(value = "age_from", defaultValue = "0")String ageFrom,
                                                             @RequestParam(value = "age_to", defaultValue = "120")String ageTo,
                                                             @RequestParam(value = "country", defaultValue = "")String country,
                                                             @RequestParam(value = "city", defaultValue = "")String city,
                                                             @RequestParam(value = "offset", defaultValue = "0") String offset,
                                                             @RequestParam(value = "itemPerPage", defaultValue = "20")String itemPerPage ){
        logger.info("Call GET /api/v1/users/search");
        return ResponseEntity.ok(personService.searchPerson(firstName, lastName, ageFrom, ageTo, country, city, offset, itemPerPage));
    }

}
