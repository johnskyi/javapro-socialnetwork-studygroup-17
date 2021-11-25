package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.dto.PersonSearchResponse;

import java.security.Principal;

public interface PersonController {

    @GetMapping("/me")
    ResponseEntity<PersonResponse> getPersonDetail(Principal principal);

    @PutMapping("/me")
    ResponseEntity<PersonResponse> putPersonDetail(PersonRequest request, Principal principal);

    @DeleteMapping("/me")
    ResponseEntity<PersonResponse> deletePerson(Boolean isHardDelete, Principal principal);

    @GetMapping("/{id}")
    ResponseEntity<PersonResponse> getPersonById(@PathVariable Long id);

    @GetMapping("/search")
    ResponseEntity<PersonSearchResponse> searchPerson(@RequestParam(value = "first_name", required = false)String firstName,
                                                             @RequestParam(value = "last_name", required = false)String lastName,
                                                             @RequestParam(value = "age_from", required = false)String ageFrom,
                                                             @RequestParam(value = "age_to", required = false)String ageTo,
                                                             @RequestParam(value = "country_id", required = false)String countryId,
                                                             @RequestParam(value = "city_id", required = false)String cityId,
                                                             @RequestParam(value = "offset", required = false) String offset,
                                                             @RequestParam(value = "itemPerPage", defaultValue = "20")String itemPerPage );

}
