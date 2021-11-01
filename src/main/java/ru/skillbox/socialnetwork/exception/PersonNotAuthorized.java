package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonNotAuthorized extends RuntimeException{
    public PersonNotAuthorized(String message) {
        super(message);
        log.error(message);
    }
}
