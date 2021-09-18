package ru.skillbox.socialnetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.model.dto.PersonResponse;
import ru.skillbox.socialnetwork.model.dto.PersonResponse.Data;
import ru.skillbox.socialnetwork.model.repositories.PersonRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public PersonResponse getUserDetail(Principal principal) {
        Person person = personRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String[] location = person.split(" ");
        return PersonResponse.builder()
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
                        .country(location[0])
                        .city(location[1])
                        .messagePermission(person.getMessagePermission())
                        .lastOnlineTime(person.getLastOnlineTime())
                        .isBlocked(person.isBlocked())
                        .build())
                .build();
    }
}
