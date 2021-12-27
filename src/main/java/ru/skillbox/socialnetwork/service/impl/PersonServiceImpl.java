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
import ru.skillbox.socialnetwork.data.entity.Town;
import ru.skillbox.socialnetwork.data.repository.CountryRepository;
import ru.skillbox.socialnetwork.data.repository.FileRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.TownRepository;
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
    private final CountryRepository countryRepository;
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
    public PersonResponse deletePerson(Boolean isHardDelete, Principal principal) {
        Person person = findPerson(principal);

        if(isHardDelete) {
            personRepository.delete(person);
        } else {
            person.setPhoto("https://static.thenounproject.com/png/438810-200.png");
            person.setApproved(false);
            person.setDeleted(true);
            personRepository.save(person);
        }
        SecurityContextHolder.clearContext();
        return createSmallPersonResponse();
    }

    @Override
    public PersonResponse getPersonById(Long id) {
        Person person = personRepository.getById(id);
        return createFullPersonResponse(person);
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
                                             String ageTo, String countryId, String city, String offset, String itemPerPage){
        Pageable pageable = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(itemPerPage));
        LocalDateTime dateFromBirth = LocalDateTime.now().minusYears(Integer.parseInt(ageTo));
        LocalDateTime dateToBirth = LocalDateTime.now().minusYears(Integer.parseInt(ageFrom));
        Page<Person> resultSearch = personRepository.findAllBySearchFilter(
                firstName.equals("") ? null : firstName,
                lastName.equals("") ? null : lastName,
                dateFromBirth,
                dateToBirth,
                city.equals("") ? null : city,
                countryId.equals("") ? null : Long.parseLong(countryId),
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
        if (request.getData().getFirstName() != null && request.getData().getFirstName().matches("[\\wа-яёА-ЯЁa-zA-Z]{2,30}")) {
            person.setFirstName(request.getData().getFirstName());
        }

        if (request.getData().getLastName() != null && request.getData().getLastName().matches("[\\wа-яёА-ЯЁa-zA-Z]{2,30}")) {
            person.setLastName(request.getData().getLastName());
        }

        if (request.getData().getBirthDate() != null) {
            person.setBirthTime(OffsetDateTime.parse( request.getData().getBirthDate() ).toLocalDateTime());
        }


        if (request.getData().getPhone() != null && request.getData().getPhone().matches("[0-9]{11}")) {
            person.setPhone(request.getData().getPhone());
        }

        if (request.getData().getPhotoId() != null) {
            File file = fileRepository.getById(request.getData().getPhotoId());
            person.setPhoto(file.getRelativeFilePath());
        }

        if (request.getData().getAbout() != null) {
            person.setAbout(request.getData().getAbout());
        }

        if (request.getData().getMessagePermission() != null) {
            person.setMessagePermission(request.getData().getMessagePermission());
        }

        if (request.getData().getTown() != null) {
            Town town = townRepository.findById(request.getData().getTown()).orElse(null);
            person.setTown(town != null ? town : person.getTown());
        }
        personRepository.save(person);
        personRepository.flush();
    }

//    private String getFormattedPhone(String phone) {
//        phone = phone.replaceAll("\\D", "");
//        return String.format("+7%s", phone.matches("[78]\\d{10}")
//                ? phone.substring(1)
//                : phone);
//    }

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
                        .isDeleted(person.isDeleted())
                        .build())
                .build();
    }
}
