package ru.skillbox.socialnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.socialnetwork.data.dto.ConfirmUserRequest;
import ru.skillbox.socialnetwork.data.dto.RegisterRequest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.yaml")
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountController accountController;

    @Test
    @DisplayName("User successfully register")
    public void regPerson() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("12345@mail.ru", "1111111", "1111111", "Имя",  "Фамилия");
        accountController.regPerson(registerRequest);
        verify(accountController,times(1)).regPerson(registerRequest);
    }

    @Test
    @DisplayName("User successfully confirm")
    public void confirmPerson() throws Exception {
        ConfirmUserRequest confirmUserRequest = new ConfirmUserRequest(1L,"1234");
        accountController.confirmPerson(confirmUserRequest);
        verify(accountController,times(1)).confirmPerson(confirmUserRequest);
    }
}
