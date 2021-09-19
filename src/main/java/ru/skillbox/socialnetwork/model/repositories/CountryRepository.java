package ru.skillbox.socialnetwork.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.model.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
