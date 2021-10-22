package ru.skillbox.socialnetwork.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(long id) {
        super("invalid person ID: " + id);
    }

    public PersonNotFoundException(String username) {
        super("invalid person username: " + username);
    }
}
