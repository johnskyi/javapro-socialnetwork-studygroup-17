package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skillbox.socialnetwork.annotations.NullOrPattern;
import ru.skillbox.socialnetwork.data.entity.MessagePermission;

import java.time.LocalDateTime;

@Data
public class PersonRequest {
    @JsonProperty("first_name")
    @NullOrPattern(pattern = "\\w{2,30}",
            message = "Имя указано неверно (допускается от 2 до 30 буквенно-цифровых символов или знак подчёркивания.")
    private String firstName;

    @JsonProperty("last_name")
    @NullOrPattern(pattern = "\\w{2,30}",
            message = "амилия указана неверно (допускается от 2 до 30 буквенно-цифровых символов или знак подчёркивания.")
    private String lastName;

    @JsonProperty("birth_date")
    private LocalDateTime birthDate;

    @NullOrPattern(pattern = "^\\+?[78]?-?\\s?\\(?\\d{3}\\)?-?\\s?\\d{3}-?\\s?\\d{2}-?\\s?\\d{2}$",
            message = "Неверный формат телефона")
    private String phone;

    @JsonProperty("photo_id")
    private Long photoId;

    private String about;

    @JsonProperty("town_id")
    private Long townId;

    @JsonProperty("country_id")
    private Long countryId;

    @JsonProperty("messages_permission")
    private MessagePermission messagePermission;
}
