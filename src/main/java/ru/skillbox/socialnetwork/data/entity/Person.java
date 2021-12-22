package ru.skillbox.socialnetwork.data.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "reg_date")
    private LocalDateTime regTime;

    @Column(name = "birth_date")
    private LocalDateTime birthTime;

    @Column(name = "e_mail")
    private String email;

    private String phone;

    private String password;

    private String photo;

    private String about;

    private String gender;

    @ManyToOne
    private Town town;

    @Column(name = "confirmation_code")
    private String code;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_permission", length = 8, nullable = false)
    private MessagePermission messagePermission = MessagePermission.ALL;

    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 16)
    private UserType type;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> likes;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCommentLike> postCommentLikes;

    @OneToMany(mappedBy = "personRequestFriend", cascade = CascadeType.ALL)
    private List<Friendship> friendshipsRequest;

    @OneToMany(mappedBy = "personReceiveFriend", cascade = CascadeType.ALL)
    private List<Friendship> friendshipsReceives;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Message> authorMessages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Message> recipientMessages;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dialog> authorDialog;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dialog> recipientDialog;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlockHistory> blockHistories;

    public Role getRole() {
        if (type.name().equals("USER")) {
            return Role.USER;
        }
        return type.name().equals("MODERATOR") ? Role.MODERATOR : Role.ADMIN;
    }
}
