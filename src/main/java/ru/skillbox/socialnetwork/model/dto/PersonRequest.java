package ru.skillbox.socialnetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skillbox.socialnetwork.annotations.NullOrPattern;
import ru.skillbox.socialnetwork.model.entities.MessagePermission;

@Data
public class PersonRequest {
    @JsonProperty("first_name")
    @NullOrPattern(pattern = "\\w{2,30}",
            message = "Имя указано неверно (допускается от 2 до 30 буквенно-цифровых символов или знак подчёркивания.")
    String firstName;

    @JsonProperty("last_name")
    @NullOrPattern(pattern = "\\w{2,30}",
            message = "амилия указана неверно (допускается от 2 до 30 буквенно-цифровых символов или знак подчёркивания.")
    String lastName;

    @JsonProperty("birth_date")
    Long birthDate;

    @NullOrPattern(pattern = "^\\+?[78]?[-\\(]?\\d{3}\\)?-?\\d{3}-?\\d{2}-?\\d{2}$",
            message = "Неверный формат телефона")
    String phone;

    @JsonProperty("photo_id")
    String photoId;
    String about;

    @JsonProperty("town_id")
    String townId;

    @JsonProperty("country_id")
    String countryId;

    @JsonProperty("messages_permission")
    MessagePermission messagePermission;
}
