package ru.skillbox.socialnetwork.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skillbox.socialnetwork.exceptions.FieldNotValidException;
import ru.skillbox.socialnetwork.model.dto.ErrorResponse;

public interface AdviceController {

    ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UsernameNotFoundException exception);

    ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(FieldNotValidException exception);
}
