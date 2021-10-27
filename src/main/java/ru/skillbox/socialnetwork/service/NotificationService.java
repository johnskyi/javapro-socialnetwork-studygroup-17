package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.Notifications.NotificationResponse;

import java.security.Principal;

public interface NotificationService {

    NotificationResponse getNotifications(String offset, String itemPerPage, Principal principal);

    NotificationResponse putNotifications(Long id, Boolean all, Principal principal);
}
