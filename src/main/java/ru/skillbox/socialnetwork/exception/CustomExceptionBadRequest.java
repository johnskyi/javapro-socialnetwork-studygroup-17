package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomExceptionBadRequest extends RuntimeException {

    public CustomExceptionBadRequest(String message) {
        super(message);
        log.error(message);
    }
}
