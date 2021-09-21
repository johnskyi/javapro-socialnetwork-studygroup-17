package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tag;

}
