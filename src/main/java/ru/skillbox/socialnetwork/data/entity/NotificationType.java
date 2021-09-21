package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int code;

    @Column(name = "name", length = 32)
    private String name;
}
