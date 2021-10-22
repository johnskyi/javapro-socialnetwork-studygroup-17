package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.Notifications.NotificationResponse;
import ru.skillbox.socialnetwork.data.entity.Notification;
import ru.skillbox.socialnetwork.data.repository.NotificationRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.service.NotificationService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final PersonRepo personRepository;

    @Override
    public NotificationResponse getNotifications(String offset, String itemPerPage, Principal principal) {
        Pageable pageable = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(itemPerPage));
        Page<Notification> notifications = notificationRepository.findAllByPersonId(
                personRepository.findByEmail(principal.getName()).get().getId(), pageable);

        List<NotificationResponse.Data> data = new ArrayList<>();
        for (Notification notification : notifications) {
            data.add(new NotificationResponse.Data(notification, personRepository.getById(notification.getEntityId())));
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
    public NotificationResponse putNotifications(Long id, Boolean all, Principal principal) {
        if(all){
            Iterable<Notification> notifications = notificationRepository.findAllByPersonId(
                    personRepository.findByEmail(principal.getName()).get().getId()
            );
            for (Notification notification : notifications) {
                System.out.println("id -" + notification.getId() + "notification.getPerson.getId " + notification.getPerson().getId());
            }
            List<Long> notificationsId = new ArrayList<>();
            List<NotificationResponse.Data> data = new ArrayList<>();
            for (Notification notification : notifications) {
                data.add(new NotificationResponse.Data(notification, personRepository.getById(notification.getEntityId())));
                notificationsId.add(notification.getId());
                notificationRepository.deleteNotificationById(notification.getId());
            }
            return NotificationResponse.builder()
                    .error("string")
                    .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                    .total(Long.parseLong(String.valueOf(notificationsId.size())))
                    .offset(0L)
                    .perPage(20L)
                    .data(data)
                    .build();
        }

        Notification notification = notificationRepository.getById(id);
        List<NotificationResponse.Data> data = List.of(new NotificationResponse.Data(notification, personRepository.getById(notification.getEntityId())));
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
