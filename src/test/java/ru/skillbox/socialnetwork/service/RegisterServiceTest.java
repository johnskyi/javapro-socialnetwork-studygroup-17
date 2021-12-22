package ru.skillbox.socialnetwork.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.socialnetwork.controller.AccountController;
import ru.skillbox.socialnetwork.data.dto.ConfirmUserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterServiceTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountController accountController;

//    @Test
//    @DisplayName("User successfully register")
//    @Disabled
//    public void regPerson() throws Exception{
//        String email = (Math.random() * 49 + 1) + "@mail.ru";
//        RegisterRequest registerRequest = new RegisterRequest(email, "1111111", "1111111", "Имя",  "Фамилия");
//        mockMvc.perform(
//                post("/api/v1/account/register/")
//                        .content(objectMapper.writeValueAsString(registerRequest))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk());
//    }

    @Test
    @DisplayName("User successfully confirm")
    @Disabled
    public void confirmPerson() throws Exception {
        ConfirmUserRequest confirmUserRequest = new ConfirmUserRequest(1L,"1234");
        mockMvc.perform(
                post("/api/v1/account/register/confirm")
                        .content(objectMapper.writeValueAsString(confirmUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    void testRegPerson() {
    }

    @Test
    void testConfirmPerson() {
    }
}
