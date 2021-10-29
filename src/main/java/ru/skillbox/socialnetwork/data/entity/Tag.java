package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tag;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post2tag",
    joinColumns = {@JoinColumn(name = "tag_id")},
    inverseJoinColumns = {@JoinColumn(name = "post_id")})
    private List<Post> posts;

    public Tag(String tag) {
        this.tag = tag;
    }
}
