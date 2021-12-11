package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.dto.PersonSearchResponse;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.security.Principal;

public interface PersonService {

    PersonResponse getPersonDetail(Principal principal);

    PersonResponse putPersonDetail(PersonRequest personRequest, Principal principal);

    PersonResponse deletePerson(Boolean isHardDelete, Principal principal);

    PersonResponse getPersonById(Long id);

     Person getCurrentUser();

     PersonSearchResponse searchPerson(String firstName, String lastName, String ageFrom,
                                             String ageTo, String countryId, String cityId, String offset, String itemPerPage);

//    ResidencyResponse getPersonResidency(ResidencyRequest request);
}
