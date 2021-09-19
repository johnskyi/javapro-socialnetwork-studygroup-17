package ru.skillbox.socialnetwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skillbox.socialnetwork.exceptions.FieldNotValidException;
import ru.skillbox.socialnetwork.model.dto.ErrorResponse;

@ControllerAdvice
public class AdviceControllerImpl implements AdviceController {

    @Override
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UsernameNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse("invalid_request", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(FieldNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(FieldNotValidException exception) {
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

}
