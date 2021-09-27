package ru.skillbox.socialnetwork.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "reg_date")
    private LocalDateTime regTime;

    @Column(name = "birth_date")
    private LocalDateTime birthTime;

    @Column(name = "email")
    private String email;

    private String phone;

    private String password;

    private String photo;

    private String about;

    @ManyToOne
    private Town town;

    @Column(name = "code")
    private String code;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_permission", length = 8, nullable = false)
    private MessagePermission messagePermission = MessagePermission.ALL;

    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 16)
    private UserType type;
}
