package ru.skillbox.socialnetwork.model.entities;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Person author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private Person recipient;


    @Column(name = "message_text")
    private String text;

    @Column(name = "read_status")
    private ReadStatus readStatus;

}
