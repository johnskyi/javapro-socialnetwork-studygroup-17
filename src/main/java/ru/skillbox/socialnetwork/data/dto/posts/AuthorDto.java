package ru.skillbox.socialnetwork.data.dto.posts;

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

import java.time.ZoneOffset;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class AuthorDto {
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

    @JsonProperty("city")
    private Town town;
    private Country country;

    @JsonProperty("messages_permission")
    private MessagePermission messagePermission;

    @JsonProperty("last_online_time")
    private Long lastOnlineTime;

    @JsonProperty("is_blocked")
    private Boolean isBlocked;

    public AuthorDto(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.regDate = person.getRegTime().toEpochSecond(ZoneOffset.UTC);
        this.birthDate = person.getBirthTime() == null ? null : person.getBirthTime().toEpochSecond(ZoneOffset.UTC);
        this.email = person.getEmail() == null ? null : person.getEmail();
        this.phone = person.getPhone() == null ? null : person.getPhone();
        this.photo = person.getPhoto() == null ? null : person.getPhoto();
        this.about = person.getAbout() == null ? null : person.getAbout();
        this.town = person.getTown() == null ? null : person.getTown();
        this.country = person.getTown() == null ? null : person.getTown().getCountry();
        this.messagePermission = person.getMessagePermission();
        this.lastOnlineTime = person.getLastOnlineTime().toEpochSecond(ZoneOffset.UTC);
        this.isBlocked = person.isBlocked();
    }
}
