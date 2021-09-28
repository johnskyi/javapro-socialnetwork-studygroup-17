package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friendship_status")
public class FriendshipStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime time;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", length = 16)
    private FriendshipStatusType code;

}
