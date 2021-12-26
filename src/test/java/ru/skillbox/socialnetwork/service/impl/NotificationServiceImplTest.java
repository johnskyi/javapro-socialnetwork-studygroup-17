package ru.skillbox.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skillbox.socialnetwork.data.dto.NotificationResponse;
import ru.skillbox.socialnetwork.data.entity.Notification;
import ru.skillbox.socialnetwork.data.entity.NotificationSettings;
import ru.skillbox.socialnetwork.data.entity.NotificationType;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.NotificationRepository;
import ru.skillbox.socialnetwork.data.repository.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class NotificationServiceImplTest {

    @Autowired
    private NotificationServiceImpl notificationService;

    @MockBean
    private PersonRepo personRepository;
    @MockBean
    private NotificationRepository notificationRepository;
    @MockBean
    private NotificationSettingsRepository notificationSettingsRepository;
    @MockBean
    private Principal principal;

    private static Person person;
    private static Person targetPerson;
    private static NotificationSettings notificationSettings;
    private static Notification newPostNotification;
    private static Notification newFriendRequestNotification;
    private static Notification newPostCommentNotification;
    private static Notification newCommentOnCommentNotification;


    static {
        notificationSettings = new NotificationSettings();
        notificationSettings.setId(1L);
        notificationSettings.setMessage(true);
        notificationSettings.setCommentOnComment(true);
        notificationSettings.setFriendRequest(true);
        notificationSettings.setPostComment(true);
        notificationSettings.setPost(true);
    }

    @BeforeAll
    static void init(){
        person = new Person();
        person.setId(1L);
        person.setEmail("Test@mail.com");

        targetPerson = new Person();
        targetPerson.setId(2L);
        targetPerson.setBirthTime(LocalDateTime.now());

        notificationSettings.setPerson(person);

        newPostNotification = new Notification(NotificationType.POST, LocalDateTime.now(), person, targetPerson.getId(), "Contact");
        newFriendRequestNotification = new Notification(NotificationType.FRIEND_REQUEST, LocalDateTime.now(), person, targetPerson.getId(), "Contact");
        newPostCommentNotification = new Notification(NotificationType.POST_COMMENT, LocalDateTime.now(), person, targetPerson.getId(), "Contact");
        newCommentOnCommentNotification = new Notification(NotificationType.COMMENT_COMMENT, LocalDateTime.now(), person, targetPerson.getId(), "Contact");
    }

    @Test
    void getNotifications() {
        Mockito.when(personRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(person));
        Mockito.when(notificationRepository.findAllByPerson(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of(newPostNotification, newPostCommentNotification, newCommentOnCommentNotification, newFriendRequestNotification)));
        Mockito.when(personRepository.getById(Mockito.any())).thenReturn(targetPerson);
        Mockito.when(principal.getName()).thenReturn(person.getEmail());
        Mockito.when(notificationSettingsRepository.findByPerson(Mockito.any())).thenReturn(notificationSettings);
        NotificationResponse response = notificationService.getNotifications("0", "10", principal);

        assertEquals(4L, response.getTotal());
        assertEquals(4L, response.getData().size());
    }

    @Test
    void putNotifications_With_DeleteAllNotifications() {
        Mockito.when(personRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(person));
        Mockito.when(notificationRepository.findAllByPersonId(Mockito.any())).thenReturn(List.of(newPostNotification, newPostCommentNotification, newCommentOnCommentNotification, newFriendRequestNotification));
        Mockito.when(personRepository.getById(Mockito.any())).thenReturn(targetPerson);

        NotificationResponse response = notificationService.putNotifications(1L, true, principal);

        assertEquals(4L, response.getTotal());
        assertEquals(4L, response.getData().size());
        verify(notificationRepository, times(4)).deleteNotificationById(Mockito.any());
        verify(notificationRepository, times(1)).findAllByPersonId(Mockito.any());
    }

    @Test
    void putNotifications_ById(){
        Mockito.when(notificationRepository.getById(Mockito.any())).thenReturn(newPostNotification);
        Mockito.when(personRepository.getById(Mockito.any())).thenReturn(targetPerson);

        NotificationResponse response = notificationService.putNotifications(1L, false, principal);

        assertEquals(1L, response.getTotal());
        assertEquals(1L, response.getData().size());
        verify(notificationRepository, times(1)).deleteNotificationById(Mockito.any());

    }
}