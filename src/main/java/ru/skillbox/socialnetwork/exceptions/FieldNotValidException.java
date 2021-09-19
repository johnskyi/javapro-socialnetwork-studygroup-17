package ru.skillbox.socialnetwork.exceptions;

import ru.skillbox.socialnetwork.model.dto.ErrorResponse;

public class FieldNotValidException extends RuntimeException{
    private final ErrorResponse errorResponse;

    public FieldNotValidException(String message) {
        errorResponse = new ErrorResponse("invalid_request", message);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
