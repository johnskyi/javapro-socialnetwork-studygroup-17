package ru.skillbox.socialnetwork.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.model.entities.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Transactional
    Optional<Person> findByEmail(String email);
}
