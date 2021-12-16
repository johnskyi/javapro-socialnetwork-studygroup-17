package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.MessagePermission;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Town;
import ru.skillbox.socialnetwork.service.ConvertTimeService;

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

        private Boolean isDeleted;

        private String message;

        private String token;

        public Data(Person person) {
            this.id = person.getId();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            if (person.getRegTime() != null) this.regDate = ConvertTimeService.getTimestamp(person.getRegTime());
            if (person.getBirthTime() != null) this.birthDate = ConvertTimeService.getTimestamp(person.getBirthTime());
            this.email = person.getEmail();
            this.phone = person.getPhone();
            this.photo = person.getPhoto();
            this.about = person.getAbout();
            this.town = person.getTown();
            this.country = person.getTown().getCountry();
            this.messagePermission = person.getMessagePermission();
            if (person.getLastOnlineTime() != null) {
                this.lastOnlineTime = ConvertTimeService.getTimestamp(person.getLastOnlineTime());
            }
            this.isBlocked = person.isBlocked();
            this.isDeleted = person.isDeleted();
        }
    }
}
