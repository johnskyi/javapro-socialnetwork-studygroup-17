package ru.skillbox.socialnetwork.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.skillbox.socialnetwork.controller.impl.PersonControllerImpl;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.MessagePermission;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Town;
import ru.skillbox.socialnetwork.data.repository.CountryRepository;
import ru.skillbox.socialnetwork.data.repository.FileRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.TownRepository;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.service.impl.PersonServiceImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "application.yaml", classes = {PersonServiceImpl.class, PersonControllerImpl.class})
class PersonControllerTest {
    @MockBean
    private PersonRepo personRepository;

    @MockBean
    private TownRepository townRepository;

    @MockBean
    private CountryRepository countryRepository;

    @MockBean
    private FileRepository fileRepository;

    @Autowired
    private PersonController personController;
    @Mock
    private Principal principal;

    private static Validator validator;

    private static final Country country;
    private static final Town town;
    private static Person person;
    private static PersonResponse personResponseForGetDetail;
    private static PersonResponse personResponseForPutDetail;
    private static PersonRequest personRequest;

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
        person.setFirstName("firstName");
        person.setLastName("lastName");
        person.setRegTime(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC));
        person.setBirthTime(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC));
        person.setEmail("test@test.com");
        person.setPhone("+71002003040");
        person.setPhoto("http://1.jpg");
        person.setPassword("12345678");
        person.setAbout("Grand Gatsby");
        person.setTown(town);
        person.setCode("code");
        person.setApproved(true);
        person.setLastOnlineTime(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC));
        person.setBlocked(false);
    }

    @BeforeAll
    static void initPersonResponseForGetDetail() {
        personResponseForGetDetail = PersonResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(PersonResponse.Data.builder()
                        .id(1L)
                        .firstName("firstName")
                        .lastName("lastName")
                        .regDate(1L)
                        .birthDate(1L)
                        .email("test@test.com")
                        .phone("+71002003040")
                        .photo("http://1.jpg")
                        .about("Grand Gatsby")
                        .country(country)
                        .town(town)
                        .messagePermission(MessagePermission.ALL)
                        .lastOnlineTime(1L)
                        .isBlocked(false)
                        .isDeleted(false)
                        .build())
                .build();
    }

    @BeforeAll
    static void initPersonRequest() {
        personRequest = new PersonRequest();
        PersonRequest.Data.builder()
                .firstName("new first name")
                .lastName("new Last name")
                .birthDate("1988-02-23T00:00:00+03:00")
                .phone("+72002002020")
                .photoId(1L)
                .about("No.. I'm teapot")
                .country(country.getId())
                .messagePermission(MessagePermission.FRIENDS)
                .build();
    }

    @BeforeAll
    static void initPersonResponseForPutDetail() {
        personResponseForPutDetail = PersonResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(PersonResponse.Data.builder()
                        .id(1L)
                        .firstName("new first name")
                        .lastName("new Last name")
                        .regDate(1L)
                        .birthDate(1L)
                        .email("test@test.com")
                        .phone("+72002002020")
                        .photo("http://2.jpg")
                        .about("No.. I'm teapot")
                        .country(country)
                        .town(town)
                        .messagePermission(MessagePermission.FRIENDS)
                        .lastOnlineTime(1L)
                        .isBlocked(false)
                        .isDeleted(false)
                        .build())
                .build();
    }

    @BeforeAll
    static void initValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("Get person details is successful")
    public void getPersonDetailTest() {
        when(principal.getName()).thenReturn("test@test.com");
        when(personRepository.findByEmail(any())).thenReturn(Optional.of(person));
        when(townRepository.getById(any())).thenReturn(town);
        assertEquals(Objects.requireNonNull(personController.getPersonDetail(principal).getBody(),
                "getPersonDetail method turned out to be null").getData(), personResponseForGetDetail.getData());
    }

    @Test
    @DisplayName("Get person details. Person unauthorized")
    public void getPersonDetailWithUnauthorizedTest() {
        assertThrows(PersonNotAuthorized.class, () -> personController.getPersonDetail(principal));
    }
    @Test
    @DisplayName("Update person details. Incorrect firstname format")
    public void putPersonDetailWrongFirstName() {
        PersonRequest request = new PersonRequest();
        request.setData(PersonRequest.Data.builder().firstName("1").build());
        Set<ConstraintViolation<PersonRequest.Data>> violations = validator.validate(request.getData());
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Update person details. Incorrect lastname format")
    public void putPersonDetailWrongLastName() {
        PersonRequest request = new PersonRequest();
        request.setData(PersonRequest.Data.builder().lastName("1").build());
        Set<ConstraintViolation<PersonRequest.Data>> violations = validator.validate(request.getData());
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Update person details. Incorrect phone format")
    public void putPersonDetailWrongPhone() {
        PersonRequest request = new PersonRequest();
        request.setData(PersonRequest.Data.builder().phone("7900900909011").build());
        Set<ConstraintViolation<PersonRequest.Data>> violations = validator.validate(request.getData());
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Update person details. Correct phone format")
    public void putPersonDetailCorrectPhone() {
        PersonRequest request = new PersonRequest();
        request.setData(PersonRequest.Data.builder().phone("7-(900) 300-20-20").build());
        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}