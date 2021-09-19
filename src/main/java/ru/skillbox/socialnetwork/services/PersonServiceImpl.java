package ru.skillbox.socialnetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.model.dto.PersonRequest;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;
import ru.skillbox.socialnetwork.model.dto.PersonResponse.Data;
import ru.skillbox.socialnetwork.model.dto.ResidencyRequest;
import ru.skillbox.socialnetwork.model.dto.ResidencyResponse;
import ru.skillbox.socialnetwork.model.entities.Person;
import ru.skillbox.socialnetwork.model.repositories.CityRepository;
import ru.skillbox.socialnetwork.model.repositories.CountryRepository;
import ru.skillbox.socialnetwork.model.repositories.PersonRepository;
import ru.skillbox.socialnetwork.model.repositories.RegionRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;

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

    @Override
    public ResidencyResponse getPersonResidency(ResidencyRequest request) {
        ResidencyResponse response = new ResidencyResponse();
        response.setCountry(countryRepository.findAll());

        if (Objects.nonNull(request.getCountry())) {
            response.setRegion(regionRepository.findAllRegionByCountry(request.getCountry()));
        }

        if (Objects.nonNull((request.getRegion()))) {
            response.setCity(cityRepository.findCitiesByRegion(request.getRegion()));
        }
        return response;
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
            person.setBirthTime(request.getBirthDate());
        }

        if (Objects.nonNull(request.getPhone())) {
            person.setPhone(getFormattedPhone(request.getPhone()));
        }

        if (Objects.nonNull(request.getPhotoId())) {
            person.setPhoto(request.getPhotoId());
        }

        if (Objects.nonNull(request.getAbout())) {
            person.setAbout(request.getAbout());
        }

        if (Objects.isNull(request.getMessagePermission())) {
            person.setMessagePermission(request.getMessagePermission());
        }

        if (Objects.nonNull(request.getCountry())) {
            person.setCountry(request.getCountry());
        }

        if (Objects.nonNull(request.getRegion())) {
            person.setRegion(request.getRegion());
        }

        if (Objects.nonNull(request.getCity())) {
            person.setCity(request.getCity());
        }

        personRepository.flush();
    }

    private String getFormattedPhone(String phone) {
        phone = phone.replaceAll("\\D", "");
        return String.format("+7%s", phone.matches("7|8\\d{10}")
                ? phone.substring(1, 10)
                : phone);
    }

    private Person findPerson(Principal principal) {
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
                        .regDate(person.getRegTime())
                        .birthDate(person.getBirthTime())
                        .email(person.getEmail())
                        .phone(person.getPhone())
                        .photo(person.getPhoto())
                        .about(person.getAbout())
                        .country(person.getCountry())
                        .region(person.getRegion())
                        .city(person.getCity())
                        .messagePermission(person.getMessagePermission())
                        .lastOnlineTime(person.getLastOnlineTime())
                        .isBlocked(person.isBlocked())
                        .build())
                .build();
    }
}
