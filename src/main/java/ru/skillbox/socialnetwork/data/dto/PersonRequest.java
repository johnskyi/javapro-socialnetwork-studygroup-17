package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.annotations.NullOrPattern;
import ru.skillbox.socialnetwork.data.entity.MessagePermission;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {
    private Data data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        @JsonProperty("first_name")
        @NullOrPattern(pattern = "[\\wа-яёА-ЯЁ]{2,30}",
                message = "Имя указано неверно (допускается от 2 до 30 буквенно-цифровых символов или знак подчёркивания.")
        private String firstName;

        @JsonProperty("last_name")
        @NullOrPattern(pattern = "[\\wа-яёА-ЯЁ]{2,30}",
                message = "Фамилия указана неверно (допускается от 2 до 30 буквенно-цифровых символов или знак подчёркивания.")
        private String lastName;

        @JsonProperty("birth_date")
        private String birthDate;

        @NullOrPattern(pattern = "^\\+?[78]?-?\\s?\\(?\\d{3}\\)?-?\\s?\\d{3}-?\\s?\\d{2}-?\\s?\\d{2}$",
                message = "Неверный формат телефона")
        private String phone;

        @JsonProperty("photo_id")
        private Long photoId;

        private String about;

        private Long town;

        private Long country;

        @JsonProperty("messages_permission")
        private MessagePermission messagePermission;
    }
}
