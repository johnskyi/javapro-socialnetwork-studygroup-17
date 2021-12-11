package ru.skillbox.socialnetwork.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;


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
