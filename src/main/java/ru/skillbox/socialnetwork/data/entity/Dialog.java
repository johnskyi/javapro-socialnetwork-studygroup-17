package ru.skillbox.socialnetwork.data.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "dialog")
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Person author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private Person recipient;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "messages")
    private  List<Message> messages;
}
