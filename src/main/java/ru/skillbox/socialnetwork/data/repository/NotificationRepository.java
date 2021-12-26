package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.data.entity.Notification;
import ru.skillbox.socialnetwork.data.entity.NotificationType;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.util.Collection;


@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "select n from Notification n where n.person = :person and n.type in :approvedNotifications")
    Page<Notification> findAllByPerson(@Param("person") Person person,
                                       @Param("approvedNotifications") Collection<NotificationType> approvedNotifications,
                                       Pageable pageable);

    Iterable<Notification> findAllByPersonId(Long id);

    @Modifying
    @Query(value = "delete FROM Notification n WHERE n.id = :id")
    void deleteNotificationById(@Param("id")Long id);
}
