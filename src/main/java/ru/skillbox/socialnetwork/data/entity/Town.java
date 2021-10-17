package ru.skillbox.socialnetwork.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class Town implements Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    @JsonProperty("title")
    String name;

    @JsonIgnore
    @ManyToOne
    Country country;
}
