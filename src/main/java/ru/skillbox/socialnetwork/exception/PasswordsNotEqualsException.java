package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordsNotEqualsException extends RuntimeException {
    public PasswordsNotEqualsException(String message) {
        super(message);
        log.error(message);
    }
}
