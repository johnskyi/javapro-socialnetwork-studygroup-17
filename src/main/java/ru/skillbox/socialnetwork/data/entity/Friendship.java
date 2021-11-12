package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private FriendshipStatus friendshipStatus;

    @ManyToOne
    @JoinColumn(name = "src_person_id", referencedColumnName = "id")
    private Person personRequestFriend;

    @ManyToOne
    @JoinColumn(name = "dst_person_id", referencedColumnName = "id")
    private Person personReceiveFriend;

}
