package ru.skillbox.socialnetwork.data.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "dialog_id", referencedColumnName = "id")
    private Dialog dialog;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Person author;

    private LocalDateTime time;

    @Column(name = "message_text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "read_status", length = 16)
    private ReadStatus readStatus;

    @Override
    public String toString() {
        return  author + "  " + time.toString() + "\n" + text;
    }
}
