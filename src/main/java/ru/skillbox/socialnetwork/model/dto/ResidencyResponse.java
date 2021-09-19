package ru.skillbox.socialnetwork.model.dto;

import lombok.Data;
import ru.skillbox.socialnetwork.model.entities.City;
import ru.skillbox.socialnetwork.model.entities.Country;
import ru.skillbox.socialnetwork.model.entities.Region;

import java.util.List;

@Data
public class ResidencyResponse {
    List<Country> country;
    List<Region> region;
    List<City> city;
}
