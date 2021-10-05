package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.dto.PersonResponse.Data;
import ru.skillbox.socialnetwork.data.entity.File;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.FileRepository;
import ru.skillbox.socialnetwork.data.repository.TownRepository;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.service.PersonService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

//    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepo personRepository;
    private final TownRepository townRepository;
    private final FileRepository fileRepository;

    @Override
    public PersonResponse getPersonDetail(Principal principal) {
        return createFullPersonResponse(findPerson(principal));
    }

    @Override
    public PersonResponse putPersonDetail(PersonRequest personRequest, Principal principal) {
        Person person = findPerson(principal);
        updatePersonDetail(personRequest, person);
        return createFullPersonResponse(person);
    }

    @Override
    public PersonResponse deletePerson(Principal principal) {
        Person person = findPerson(principal);
        personRepository.delete(person);
        return createSmallPersonResponse();
    }

    private PersonResponse createSmallPersonResponse() {
        return PersonResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(Data.builder()
                        .message("ok")
                        .build())
                .build();
    }

    private void updatePersonDetail(PersonRequest request, Person person) {
        if (Objects.nonNull(request.getFirstName())) {
            person.setFirstName(request.getFirstName());
        }

        if (Objects.nonNull(request.getLastName())) {
            person.setLastName(request.getLastName());
        }

        if (Objects.nonNull(request.getBirthDate())) {
            person.setBirthTime(OffsetDateTime.parse( request.getBirthDate() ).toLocalDateTime());
        }


        if (Objects.nonNull(request.getPhone())) {
            person.setPhone(getFormattedPhone(request.getPhone()));
        }

        if (Objects.nonNull(request.getPhotoId())) {
            File file = fileRepository.getById(request.getPhotoId());
            person.setPhoto(file.getRelativeFilePath());
        }

        if (Objects.nonNull(request.getAbout())) {
            person.setAbout(request.getAbout());
        }

        if (Objects.nonNull(request.getMessagePermission())) {
            person.setMessagePermission(request.getMessagePermission());
        }

        if (Objects.nonNull(request.getTownId())) {
            person.setTown(townRepository.getById(request.getTownId()));
        }

        personRepository.flush();
    }

    private String getFormattedPhone(String phone) {
        phone = phone.replaceAll("\\D", "");
        return String.format("+7%s", phone.matches("[78]\\d{10}")
                ? phone.substring(1)
                : phone);
    }

    private Person findPerson(Principal principal) {
        if (Objects.isNull(principal.getName())) {
            throw new PersonNotAuthorized("The Person not authorized");
        }
        return personRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private PersonResponse createFullPersonResponse(Person person) {
        return PersonResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(Data.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .regDate(person.getRegTime().toEpochSecond(ZoneOffset.UTC))
                        .birthDate(person.getBirthTime().toEpochSecond(ZoneOffset.UTC))
                        .email(person.getEmail())
                        .phone(person.getPhone())
                        .photo(person.getPhoto())
                        .about(person.getAbout())
                        .town(person.getTown())
                        .country(person.getTown().getCountry())
                        .messagePermission(person.getMessagePermission())
                        .lastOnlineTime(person.getLastOnlineTime().toEpochSecond(ZoneOffset.UTC))
                        .isBlocked(person.isBlocked())
                        .build())
                .build();
    }
}
