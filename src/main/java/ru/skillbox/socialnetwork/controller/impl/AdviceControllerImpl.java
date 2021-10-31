package ru.skillbox.socialnetwork.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skillbox.socialnetwork.controller.AdviceController;
import ru.skillbox.socialnetwork.data.dto.ErrorResponse;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.exception.PostNotFoundException;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Objects;

@ControllerAdvice
@ApiIgnore
@Slf4j
public class AdviceControllerImpl implements AdviceController {

    @Override
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UsernameNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse("invalid_request", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotAuthorized.class)
    public ResponseEntity<ErrorResponse> PersonNotAuthorizedExceptionHandler(PersonNotAuthorized exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse("invalid_request", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse("invalid_request", Objects.requireNonNull(
                exception.getFieldError(), "exception.getFieldError() is null").getDefaultMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<ErrorResponse> postNotFoundExceptionHandler(PostNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse("invalid_request", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}
