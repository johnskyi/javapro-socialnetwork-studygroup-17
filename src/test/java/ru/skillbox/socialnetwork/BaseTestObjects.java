package ru.skillbox.socialnetwork;

import lombok.Data;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.MessagePermission;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Town;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/** Base parent class for testing classes*/
@Data
public class BaseTestObjects {

    private  Validator validator;

    private  Country countryOneTest;
    private  Town townOneTest;
    private  Person personOneTest;
    private  PersonResponse personResponseForGetDetail;
    private  PersonResponse personResponseForPutDetail;
    private  PersonRequest personRequest;

    public void setUp() {
        {
            countryOneTest = new Country();
            countryOneTest.setId(1L);
            countryOneTest.setName("Россия");

            townOneTest = new Town();
            townOneTest.setId(1L);
            townOneTest.setName("Воронеж");
            townOneTest.setCountry(countryOneTest);
            System.out.println(1);
        }
        }
     protected  void initPerson() {
            personOneTest = new Person();
            personOneTest.setId(1L);
            personOneTest.setFirstName("firstName");
            personOneTest.setLastName("lastName");
            personOneTest.setRegTime(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC));
            personOneTest.setBirthTime(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC));
            personOneTest.setEmail("test@test.com");
            personOneTest.setPhone("+71002003040");
            personOneTest.setPhoto("http://1.jpg");
            personOneTest.setPassword("12345678");
            personOneTest.setAbout("Grand Gatsby");
            personOneTest.setTown(townOneTest);
            personOneTest.setCode("code");
            personOneTest.setApproved(true);
            personOneTest.setLastOnlineTime(LocalDateTime.ofEpochSecond(1L, 0, ZoneOffset.UTC));
            personOneTest.setBlocked(false);
        }
       protected void initPersonResponseForGetDetail() {
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
                            .country(countryOneTest)
                            .town(townOneTest)
                            .messagePermission(MessagePermission.ALL)
                            .lastOnlineTime(1L)
                            .isBlocked(false)
                            .build())
                    .build();
        }
        protected void initPersonRequest() {
            personRequest = new PersonRequest();
            personRequest.setFirstName("new first name");
            personRequest.setLastName("new Last name");
            personRequest.setBirthDate("1988-02-23T00:00:00+03:00");
            personRequest.setPhone("+72002002020");

            personRequest.setPhotoId(1L);
            personRequest.setAbout("No.. I'm teapot");
            personRequest.setCountryId(1L);
            personRequest.setTownId(1L);
            personRequest.setMessagePermission(MessagePermission.FRIENDS);
        }


        protected void initPersonResponseForPutDetail() {
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
                            .country(countryOneTest)
                            .town(townOneTest)
                            .messagePermission(MessagePermission.FRIENDS)
                            .lastOnlineTime(1L)
                            .isBlocked(false)
                            .build())
                    .build();
        }


        protected void initValidator() {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }
    }
