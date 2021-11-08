package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DialogNotFoundException extends RuntimeException{
    public DialogNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
