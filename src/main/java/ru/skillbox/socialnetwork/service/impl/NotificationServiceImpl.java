package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.NotificationResponse;
import ru.skillbox.socialnetwork.data.entity.Notification;
import ru.skillbox.socialnetwork.data.entity.NotificationSettings;
import ru.skillbox.socialnetwork.data.entity.NotificationType;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.NotificationRepository;
import ru.skillbox.socialnetwork.data.repository.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.service.NotificationService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationSettingsRepository notificationSettingsRepository;
    private final PersonRepo personRepository;

    @Override
    public NotificationResponse getNotifications(String offset, String itemPerPage, Principal principal) {
        Pageable pageable = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(itemPerPage));

        Person person = personRepository.findByEmail(principal.getName()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        NotificationSettings notificationSetting = notificationSettingsRepository.findByPerson(person);
        Set<NotificationType> approvedNotifications = notificationSetting.getApprovedNotification();
        Page<Notification> notifications = notificationRepository.findAllByPerson(
                person, approvedNotifications, pageable);

        List<NotificationResponse.Data> data = new ArrayList<>();
        for (Notification notification : notifications) {
            data.add(new NotificationResponse.Data(notification, personRepository.getById(notification.getTargetPersonId())));
        }

        return NotificationResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(notifications.getTotalElements())
                .offset(Long.parseLong(offset))
                .perPage(Long.parseLong(itemPerPage))
                .data(data)
                .build();
    }

    @Override
    public NotificationResponse putNotifications(Long id, Boolean deleteAllNotifications, Principal principal) {
        if(deleteAllNotifications){
            Iterable<Notification> notifications = notificationRepository.findAllByPersonId(
                    personRepository.findByEmail(principal.getName()).get().getId()
            );
            Long notificationRemoveCount = 0L;
            List<NotificationResponse.Data> data = new ArrayList<>();
            for (Notification notification : notifications) {
                data.add(new NotificationResponse.Data(notification, personRepository.getById(notification.getTargetPersonId())));
                notificationRemoveCount++;
                notificationRepository.deleteNotificationById(notification.getId());
            }
            return NotificationResponse.builder()
                    .error("string")
                    .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                    .total(notificationRemoveCount)
                    .offset(0L)
                    .perPage(20L)
                    .data(data)
                    .build();
        }

        Notification notification = notificationRepository.getById(id);
        List<NotificationResponse.Data> data = List.of(new NotificationResponse.Data(notification, personRepository.getById(notification.getTargetPersonId())));
        notificationRepository.deleteNotificationById(notification.getId());

        return NotificationResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(1L)
                .offset(0L)
                .perPage(1L)
                .data(data)
                .build();
    }
}
