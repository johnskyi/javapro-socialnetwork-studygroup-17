package ru.skillbox.socialnetwork.controller.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.socialnetwork.data.dto.PasswordRecoveryResponse;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Town;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.TownRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PasswordRecoveryControllerImplTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordRecoveryControllerImpl controller;
    @MockBean
    private PersonRepo personRepo;
    @MockBean
    private TownRepository townRepository;
    @MockBean
    private Principal principal;

    private static Person person;
    private static final Country country;
    private static final   Town town;
    private static PasswordRecoveryResponse passwordRecoveryResponse;

    static {
        country = new Country();
        country.setId(1L);
        country.setName("Россия");

        town = new Town();
        town.setId(1L);
        town.setName("Воронеж");
        town.setCountry(country);
        System.out.println(1);
    }
    @BeforeAll
    static void initPerson() {
        person = new Person();
        person.setId(1L);
        person.setFirstName("Петр");
        person.setLastName("Петров");
        person.setRegTime(LocalDateTime.ofEpochSecond(1, 0, ZoneOffset.UTC));
        person.setBirthTime(LocalDateTime.ofEpochSecond(1, 0, ZoneOffset.UTC));
        person.setEmail("test2@test2.com");
        person.setPhone("+71002004050");
        person.setPhoto("http://2.jpg");
        person.setPassword("12345678");
        person.setAbout("Бил Гейтс");
        person.setTown(town);
        person.setCode("2");
        person.setApproved(true);
        person.setLastOnlineTime(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC));
        person.setBlocked(false);
    }
    @Test
    @DisplayName("Check sendEmail method")
    void sendEmail() throws Exception {
        when(principal.getName()).thenReturn("test2@test2.com");
        when(personRepo.findByEmail(any())).thenReturn(Optional.of(person));
        when(townRepository.getById(any())).thenReturn(town);
        this.mockMvc.perform(put("/api/v1/account/password/recovery")
                .param("email", person.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Check sendPassword method")
    void setPassword() throws Exception {
        when(principal.getName()).thenReturn("test2@test2.com");
        when(personRepo.findByCode(any())).thenReturn(Optional.of(person));
        when(townRepository.getById(any())).thenReturn(town);
        this.mockMvc.perform(put("/api/v1/account/password/set")
                        .param("token", person.getCode())
                        .param("password",person.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Check setEmail method")
    @WithMockUser(username = "test2@test2.com",password = "12345678")
    void setEmail() throws Exception {
        when(principal.getName()).thenReturn("test2@test2.com");
        when(personRepo.findByEmail(any())).thenReturn(Optional.of(person));
        when(townRepository.getById(any())).thenReturn(town);
        this.mockMvc.perform(put("/api/v1/account/email")
                        .param("email","test3@test3.com")
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}