package ru.skillbox.socialnetwork.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private FriendshipStatus friendshipStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "src_person_id", referencedColumnName = "id")
    private Person personRequestFriend;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dst_person_id", referencedColumnName = "id")
    private Person personReceiveFriend;

}
