package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.MessagePermission;
import ru.skillbox.socialnetwork.data.entity.Town;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    String error;
    Long timestamp;
    Data data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class Data {
        private Long id;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("reg_date")
        private Long regDate;

        @JsonProperty("birth_date")
        private Long birthDate;

        private String email;
        private String phone;
        private String photo;
        private String about;
        private Town town;
        private Country country;

        @JsonProperty("messages_permission")
        private MessagePermission messagePermission;

        @JsonProperty("last_online_time")
        private Long lastOnlineTime;

        @JsonProperty("is_blocked")
        private Boolean isBlocked;

        private String message;
    }
}
