package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
