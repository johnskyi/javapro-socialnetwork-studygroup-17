package ru.skillbox.socialnetwork.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skillbox.socialnetwork.controller.AccountController;
import ru.skillbox.socialnetwork.data.dto.ConfirmUserRequest;
import ru.skillbox.socialnetwork.data.dto.RegisterRequest;
import ru.skillbox.socialnetwork.data.dto.RegisterResponse;
import ru.skillbox.socialnetwork.exception.PasswordsNotEqualsException;
import ru.skillbox.socialnetwork.exception.PersonAlReadyRegisterException;
import ru.skillbox.socialnetwork.exception.PersonNotFoundException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RegisterServiceTest {


    @Autowired
    private AccountController accountController;
    @MockBean
    private RegisterService registerService;

    RegisterRequest registerRequest;
    RegisterResponse registerResponse;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerResponse = RegisterResponse.builder()
                .timestamp(LocalDateTime.now())
                .data(RegisterResponse.Data.builder()
                        .message("ok")
                        .build())
                .build();
    }

    @Test
    @DisplayName("Register valid person")
    void regPerson_returnResponseOk() {
        when(registerService.regPerson(any(RegisterRequest.class))).thenReturn(registerResponse);
        assertEquals(ResponseEntity.ok(registerResponse),accountController.regPerson(registerRequest));
        verify(registerService,times(1)).regPerson(registerRequest);

    }
    @Test
    @DisplayName("Register valid person but passwords not equals")
    void regPerson_throwPasswordNotEquals() {
        registerRequest.setEmail("test@test.ru");
        registerRequest.setPasswd1("123");
        registerRequest.setPasswd2("321");
        when(registerService.regPerson(any())).thenThrow(PasswordsNotEqualsException.class);
        assertThrows(PasswordsNotEqualsException.class, () -> accountController.regPerson(registerRequest));
        verify(registerService,times(1)).regPerson(registerRequest);
    }
    @Test
    @DisplayName("Register valid person but person already register")
    void regPerson_throwPersonAlReadyRegister() {
        registerRequest.setEmail("test@test.ru");
        when(registerService.regPerson(any())).thenThrow(PersonAlReadyRegisterException.class);
        assertThrows(PersonAlReadyRegisterException.class, () -> accountController.regPerson(registerRequest));
        verify(registerService,times(1)).regPerson(registerRequest);
    }

    @Test
    @DisplayName("Confirm valid person")
    void confirmPerson_returnResponseOk() {
        ConfirmUserRequest request = ConfirmUserRequest.builder()
                .userId(1L)
                .build();
        when(registerService.confirmPerson(request)).thenReturn(registerResponse);
        assertEquals(ResponseEntity.ok(registerResponse),accountController.confirmPerson(request));
        verify(registerService,times(1)).confirmPerson(request);
    }
    @Test
    @DisplayName("Confirm invalid person")
    void confirmPerson_throwsPersonNotFound() {
        ConfirmUserRequest request = ConfirmUserRequest.builder()
                .userId(2L)
                .build();
        when(registerService.confirmPerson(request)).thenThrow(PersonNotFoundException.class);
        assertThrows(PersonNotFoundException.class, () -> accountController.confirmPerson(request));
        verify(registerService,times(1)).confirmPerson(request);
    }

}
