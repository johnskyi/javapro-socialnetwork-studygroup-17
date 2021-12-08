package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.socialnetwork.data.entity.Country;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("select c from Country c where c.name like %:query%")
    Page<Country> findCountriesByQuery(String query, Pageable pageable);

    Optional<Country> findByName(String name);
}
