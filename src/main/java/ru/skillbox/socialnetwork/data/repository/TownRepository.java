package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.socialnetwork.data.entity.Town;


public interface TownRepository extends JpaRepository<Town, Long> {

    @Query("select t from Town t where " +
            "(:city is null or t.name like %:city%) " +
            "and t.country.id = :countryId")
    Page<Town> findTownsByQuery(String city, Long countryId, Pageable pageable);
}
