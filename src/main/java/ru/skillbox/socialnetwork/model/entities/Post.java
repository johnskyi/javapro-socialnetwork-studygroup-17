package ru.skillbox.socialnetwork.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Person author;

    private String title;

    @Column(name = "post_text")
    private String textHtml;

    @Column(name = "is_blocked")
    private boolean isBlocked;
}
