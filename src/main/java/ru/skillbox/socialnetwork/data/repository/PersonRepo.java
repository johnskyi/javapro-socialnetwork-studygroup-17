package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Transactional
    Optional<Person> findByEmail(@Param("e_mail") String email);

    Optional<Person> findByCode(@Param("confirmation_code") String code);

    Optional<Person> findByIdAndCode(@Param("id") Long id, @Param("confirmation_code") String code);

    Optional<Person> findById(Long id);

    @Query(value = "select p from Person p join p.town t join t.country c " +
            "WHERE (:firstName is null or p.firstName LIKE %:firstName%) " +
            "AND (:lastName is null or p.lastName LIKE %:lastName%) " +
            "AND (p.birthTime >= :ageFrom AND p.birthTime <= :ageTo) " +
            "AND (:city is null or p.town.name = :city) " +
            "AND (:country is null or c.name = :country)")
    Page<Person> findAllBySearchFilter(@Param("firstName") String firstName,
                                       @Param("lastName") String lastName,
                                       @Param("ageFrom") LocalDateTime ageFrom,
                                       @Param("ageTo") LocalDateTime ageTo,
                                       @Param("country") String country,
                                       @Param("city") String city,
                                       Pageable pageable);
}
