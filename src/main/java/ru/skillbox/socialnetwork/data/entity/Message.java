package ru.skillbox.socialnetwork.data.entity;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "dialog_id", referencedColumnName = "id")
    private Dialog dialog;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Person author;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private Person recipient;

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
