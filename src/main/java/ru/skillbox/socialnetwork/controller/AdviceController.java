package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.skillbox.socialnetwork.data.dto.ErrorResponse;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.exception.PostNotFoundException;

public interface AdviceController {

    ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UsernameNotFoundException exception);

    ResponseEntity<ErrorResponse> PersonNotAuthorizedExceptionHandler(PersonNotAuthorized exception);

    ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception);

    ResponseEntity<ErrorResponse> postNotFoundExceptionHandler(PostNotFoundException exception);
}
