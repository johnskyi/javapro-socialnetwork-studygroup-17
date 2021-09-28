package ru.skillbox.socialnetwork.exception;

public class PersonNotAuthorized extends RuntimeException{
    public PersonNotAuthorized(String message) {
        super(message);
    }
}
