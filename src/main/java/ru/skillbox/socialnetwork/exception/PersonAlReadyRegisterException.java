package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonAlReadyRegisterException extends RuntimeException{
    public PersonAlReadyRegisterException(String message) {
        super(message);
        log.error(message);
    }
}
