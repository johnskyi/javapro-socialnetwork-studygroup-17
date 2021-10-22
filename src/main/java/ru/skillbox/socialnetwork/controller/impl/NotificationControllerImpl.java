package ru.skillbox.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.NotificationController;
import ru.skillbox.socialnetwork.data.dto.Notifications.NotificationResponse;
import ru.skillbox.socialnetwork.service.NotificationService;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<NotificationResponse> getNotifications(@RequestParam(value = "offset", defaultValue = "0") String offset,
                                                                 @RequestParam(value = "itemPerPage", defaultValue = "20") String itemPerPage,
                                                                 Principal principal) {
        return ResponseEntity.ok(notificationService.getNotifications(offset, itemPerPage, principal));
    }

    @Override
    public ResponseEntity<NotificationResponse> putNotifications(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                                                                @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all,
                                                                Principal principal) {
        return ResponseEntity.ok(notificationService.putNotifications(id, all, principal));
    }
}
