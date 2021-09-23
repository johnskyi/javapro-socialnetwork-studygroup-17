package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Transactional
    Optional<Person> findByEmail(@Param("e_mail") String email);

    Optional<Person> findByCode(@Param("confirmation_code") String code);
}
