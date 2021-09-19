package ru.skillbox.socialnetwork.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.socialnetwork.model.entities.Country;
import ru.skillbox.socialnetwork.model.entities.Region;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query("select r from Region r " +
           "where r.country = :country")
    List<Region> findAllRegionByCountry(Country country);
}
