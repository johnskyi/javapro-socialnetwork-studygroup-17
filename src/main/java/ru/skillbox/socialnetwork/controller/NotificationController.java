package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.skillbox.socialnetwork.data.dto.NotificationResponse;

import java.security.Principal;

public interface NotificationController {

    @GetMapping("")
    ResponseEntity<NotificationResponse> getNotifications(String offset, String itemPerPage, Principal principal);

    @PutMapping("")
    ResponseEntity<NotificationResponse> putNotifications(Long id, Boolean deleteAllNotifications, Principal principal);
}
