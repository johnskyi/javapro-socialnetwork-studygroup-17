package ru.skillbox.socialnetwork.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String email) {
        super("User with : " + email + " not found");
    }
}
