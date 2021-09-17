package ru.skillbox.socialnetwork.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friendship_status")
public class FriendshipStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long time;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", length = 16)
    private FriendshipStatusType code;

}
