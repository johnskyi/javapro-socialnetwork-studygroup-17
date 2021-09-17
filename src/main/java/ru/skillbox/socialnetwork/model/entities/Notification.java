package ru.skillbox.socialnetwork.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private NotificationType type;

    @Column(name = "sent_time")
    private long time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parson_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "entity_id")
    private int entity;

    private String contact;
}
