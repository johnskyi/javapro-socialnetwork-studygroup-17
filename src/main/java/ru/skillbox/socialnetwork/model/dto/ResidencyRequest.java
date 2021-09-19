package ru.skillbox.socialnetwork.model.dto;

import lombok.Data;
import ru.skillbox.socialnetwork.model.entities.Country;
import ru.skillbox.socialnetwork.model.entities.Region;

@Data
public class ResidencyRequest {
    Country country;
    Region region;
}
