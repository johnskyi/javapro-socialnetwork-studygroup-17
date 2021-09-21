package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post2tag")
public class Post2Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;
}
