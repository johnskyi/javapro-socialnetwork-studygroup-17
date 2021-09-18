package ru.skillbox.socialnetwork.services;

import ru.skillbox.socialnetwork.model.dto.PersonResponse;

import java.security.Principal;

public interface PersonService {

    PersonResponse getUserDetail(Principal principal);
}
