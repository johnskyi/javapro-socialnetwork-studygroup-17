package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Transactional
    Optional<Person> findByEmail(@Param("e_mail") String email);

    Optional<Person> findByCode(@Param("confirmation_code") String code);

    Optional<Person> findByIdAndCode(@Param("id") Long id, @Param("confirmation_code") String code);

    Optional<Person> findById(Long id);

    @Query(value = "select p from Person p join p.town t join t.country c " +
            "WHERE (:firstName is null or p.firstName like %:firstName% " +
                    "or p.lastName like %:firstName% " +
                    "or (concat(p.firstName, ' ', p.lastName) like %:firstName%) " +
                    "or (concat(p.lastName, ' ', p.firstName) like %:firstName%))" +
            "AND (:lastName is null or p.lastName LIKE %:lastName%) " +
            "AND (p.birthTime >= :ageFrom AND p.birthTime <= :ageTo) " +
            "AND (:countryId is null or p.town.country.id = :countryId)" +
            "AND (:city is null or p.town.name = :city) " +
            "order by p.lastName, p.firstName" )
    Page<Person> findAllBySearchFilter(@Param("firstName") String firstName,
                                       @Param("lastName") String lastName,
                                       @Param("ageFrom") LocalDateTime ageFrom,
                                       @Param("ageTo") LocalDateTime ageTo,
                                       @Param("city") String city,
                                       @Param("countryId") Long countryId,
                                       Pageable pageable);

    @Query(value = "select P from #{#entityName} P where P not in :known")
    Page<Person> findRandomRecs(List<Person> known, Pageable paging);

    @Modifying
    @Query(value = "delete from Person p WHERE p.email = :email")
    void deletePersonByEmail(@Param("email") String email);

    @Query(value = "select p from Person p WHERE p.regTime >= :date_from and p.regTime >= :date_to")
    List<Person> findAllByRegTimeBetweenDates(@Param("date_from") LocalDateTime dateFrom,
                                              @Param("date_to") LocalDateTime dateTo);
}
