package ru.skillbox.socialnetwork.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "city")
public class Town implements Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    String name;

    @ManyToOne
    //  @JoinColumn(name ="country_id", columnDefinition= "id", insertable = false, updatable = false)
    Country country;
}
