package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(long id) {
        super("invalid person ID: " + id);
    }

    public PersonNotFoundException(String username) {
        super("invalid person username: " + username);
        log.error(username);
    }
}
