package ru.skillbox.socialnetwork.model;

import lombok.Data;

import javax.persistence.*;

@Data
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
    private long regTime;

    @Column(name = "birth_date")
    private long birthTime;

    @Column(name = "e_mail")
    private String email;

    private String phone;

    private String password;

    private String photo;

    private String about;

    private String town;

    @Column(name = "confirmation_code")
    private String code;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    @Column(name = "messages_permission", length = 8, nullable = false)
    private MessagePermission messagePermission = MessagePermission.ALL;

    @Column(name = "last_online_time")
    private long lastOnlineTime;

    @Column(name = "is_blocked")
    private boolean isBlocked;
}
