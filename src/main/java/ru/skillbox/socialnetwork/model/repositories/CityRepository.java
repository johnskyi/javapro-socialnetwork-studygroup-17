package ru.skillbox.socialnetwork.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.socialnetwork.model.entities.City;
import ru.skillbox.socialnetwork.model.entities.Region;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c " +
           "where c.region = :region")
    List<City> findCitiesByRegion(Region region);
}
