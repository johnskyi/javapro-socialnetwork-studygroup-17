package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@ManyToOne(cascade = CascadeType.ALL)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 16)
    private NotificationType type;

    @Column(name = "sent_time")
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "entity_id")
    private long targetPersonId;

    private String contact;

    public Notification(NotificationType type, LocalDateTime time, Person personNotification, Long targetPersonId, String contact) {
        this.type = type;
        this.time = time;
        this.person = personNotification;
        this.targetPersonId = targetPersonId;
        this.contact = contact;
    }
}
