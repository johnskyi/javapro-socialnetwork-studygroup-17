package ru.skillbox.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(@Param("e_mail") String email);

    Optional<Person> findByToken(@Param("confirmation_code") String code);
}
