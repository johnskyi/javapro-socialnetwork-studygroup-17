package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.PersonRequest;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.dto.PersonResponse.Data;
import ru.skillbox.socialnetwork.data.dto.PersonSearchResponse;
import ru.skillbox.socialnetwork.data.entity.File;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.*;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.exception.UnauthorizedException;
import ru.skillbox.socialnetwork.service.PersonService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {


    private final PersonRepo personRepository;
    private final TownRepository townRepository;
    private final FileRepository fileRepository;
    private final NotificationRepository notificationRepository;
    private final FriendshipRepository friendshipRepository;

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
    public PersonResponse deletePerson(Boolean isHardDelete, Principal principal) {
        Person person = findPerson(principal);

        if(isHardDelete) {
            personRepository.delete(person);
        } else {
            person.setFirstName("Deleted");
            person.setLastName("User");
            person.setPhoto("https://static.thenounproject.com/png/438810-200.png");
            person.setApproved(false);
            person.setBlocked(true);
            personRepository.save(person);
        }
        SecurityContextHolder.clearContext();
        return createSmallPersonResponse();
    }

    @Override
    public Person getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new SecurityException("Session is not authorized");
        }

        String email = auth.getName();

        Optional<Person> per = personRepository.findByEmail(email);

        if (per.isEmpty()) {
            throw new UnauthorizedException(email);
        }

        return per.get();
    }

    @Override
    public PersonSearchResponse searchPerson(String firstName, String lastName, String ageFrom,
                                             String ageTo, String country, String city, String offset, String itemPerPage){
        Pageable pageable = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(itemPerPage));
        LocalDateTime dateFromBirth = LocalDateTime.now().minusYears(Integer.parseInt(ageTo));
        LocalDateTime dateToBirth = LocalDateTime.now().minusYears(Integer.parseInt(ageFrom));
        Page<Person> resultSearch = personRepository.findAllBySearchFilter(
                firstName.equals("") ? null : firstName,
                lastName.equals("") ? null : lastName,
                dateFromBirth,
                dateToBirth,
                country.equals("") ? null : country,
                city.equals("") ? null : city,
                pageable);

        List<Data> data = new ArrayList<>();
        for (Person person : resultSearch) {
            PersonResponse personResponse = createFullPersonResponse(person);
            data.add(personResponse.getData());
        }

        return PersonSearchResponse.builder()
                .error("ok")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(resultSearch.getTotalElements())
                .offset(Integer.parseInt(offset))
                .perPage(Integer.parseInt(itemPerPage))
                .data(data).build();
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
