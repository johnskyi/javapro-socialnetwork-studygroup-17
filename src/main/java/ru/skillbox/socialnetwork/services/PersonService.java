package ru.skillbox.socialnetwork.services;

import ru.skillbox.socialnetwork.model.dto.PersonRequest;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;

import java.security.Principal;

public interface PersonService {

    PersonResponse getPersonDetail(Principal principal);

    PersonResponse putPersonDetail(PersonRequest personRequest, Principal principal);
}
