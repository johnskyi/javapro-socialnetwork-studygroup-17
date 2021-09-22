package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;

import java.security.Principal;

public interface PersonService {

    PersonResponse getPersonDetail(Principal principal);

    PersonResponse putPersonDetail(PersonRequest personRequest, Principal principal);

    PersonResponse deletePerson(Principal principal);

//    ResidencyResponse getPersonResidency(ResidencyRequest request);
}
