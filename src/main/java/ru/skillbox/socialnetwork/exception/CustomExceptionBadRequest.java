package ru.skillbox.socialnetwork.exception;

public class CustomExceptionBadRequest extends RuntimeException {

    public CustomExceptionBadRequest(String message) {
        super(message);
    }
}
