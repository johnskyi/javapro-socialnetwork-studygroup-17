package ru.skillbox.socialnetwork.services;

import ru.skillbox.socialnetwork.model.dto.PersonRequest;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;
import ru.skillbox.socialnetwork.model.dto.ResidencyRequest;
import ru.skillbox.socialnetwork.model.dto.ResidencyResponse;

import java.security.Principal;

public interface PersonService {

    PersonResponse getPersonDetail(Principal principal);

    PersonResponse putPersonDetail(PersonRequest personRequest, Principal principal);

    PersonResponse deletePerson(Principal principal);

    ResidencyResponse getPersonResidency(ResidencyRequest request);
}
