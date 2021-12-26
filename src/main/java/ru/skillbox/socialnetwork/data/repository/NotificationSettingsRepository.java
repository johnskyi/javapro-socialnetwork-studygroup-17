package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.data.entity.NotificationSettings;
import ru.skillbox.socialnetwork.data.entity.Person;

@Repository
@Transactional
public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {
    NotificationSettings findByPerson(Person person);

    @Modifying
    @Query(value = "UPDATE NotificationSettings n set n.post = :enable where n.person = :person")
    void setNotificationPost(@Param("person") Person person,
                             @Param("enable") Boolean enable);

    @Modifying
    @Query(value = "UPDATE NotificationSettings n set n.postComment = :enable where n.person = :person")
    void setNotificationPostComment(@Param("person") Person person,
                                    @Param("enable") Boolean enable);

    @Modifying
    @Query(value = "UPDATE NotificationSettings  n set n.commentOnComment = :enable where n.person = :person")
    void setNotificationCommentOnComment(@Param("person") Person person,
                                         @Param("enable") Boolean enable);

    @Modifying
    @Query(value = "update NotificationSettings  n set n.friendRequest = :enable where n.person = :person")
    void setNotificationFriendRequest(@Param("person") Person person,
                                      @Param("enable") Boolean enable);

    @Modifying
    @Query(value = "UPDATE NotificationSettings n set n.message = :enable where n.person = :person")
    void setNotificationMessage(@Param("person") Person person,
                                @Param("enable") Boolean enable);
}
