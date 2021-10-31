package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String email) {
        super("User with : " + email + " not found");
        log.error(email);
    }
}
