package ru.skillbox.socialnetwork.data.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_settings")
public class NotificationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "POST")
    private boolean post;

    @Column(name = "POST_COMMENT")
    private boolean postComment;

    @Column(name = "COMMENT_COMMENT")
    private boolean commentOnComment;

    @Column(name = "FRIEND_REQUEST")
    private boolean friendRequest;

    @Column(name = "MESSAGE")
    private boolean message;

    public Set<NotificationType> getApprovedNotification(){
        Set<NotificationType> approvedNotifications = new HashSet<>();
        if (post)
            approvedNotifications.add(NotificationType.POST);
        if (postComment)
            approvedNotifications.add(NotificationType.POST_COMMENT);
        if (commentOnComment)
            approvedNotifications.add(NotificationType.COMMENT_COMMENT);
        if (friendRequest)
            approvedNotifications.add(NotificationType.FRIEND_REQUEST);
        if (message)
            approvedNotifications.add(NotificationType.MESSAGE);
        return approvedNotifications;
    }
}
