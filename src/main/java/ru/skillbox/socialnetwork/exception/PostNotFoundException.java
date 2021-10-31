package ru.skillbox.socialnetwork.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
