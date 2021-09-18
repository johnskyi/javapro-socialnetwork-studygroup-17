package ru.skillbox.socialnetwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skillbox.socialnetwork.model.dto.ErrorResponse;

public class AdviceControllerImpl implements AdviceController {

    @Override
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> getUserDetailException(UsernameNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse("invalid_request", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
