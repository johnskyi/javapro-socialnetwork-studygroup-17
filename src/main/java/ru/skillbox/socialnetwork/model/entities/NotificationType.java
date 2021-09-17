package ru.skillbox.socialnetwork.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer code;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 16)
    private NotificationTypeName name;
}
