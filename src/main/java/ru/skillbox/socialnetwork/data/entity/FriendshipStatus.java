package ru.skillbox.socialnetwork.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    public FriendshipStatus(LocalDateTime time, String name, FriendshipStatusType code) {
        this.time = time;
        this.name = name;
        this.code = code;
    }
}
