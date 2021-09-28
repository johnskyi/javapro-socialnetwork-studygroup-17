package ru.skillbox.socialnetwork.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
<<<<<<< HEAD
@Table(name = "countries")
=======
@Table(name = "country")
>>>>>>> s1/t6v2
public class Country implements Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
}
<<<<<<< HEAD
=======

>>>>>>> s1/t6v2
