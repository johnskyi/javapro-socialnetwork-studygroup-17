package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.NotificationController;
import ru.skillbox.socialnetwork.data.dto.NotificationResponse;
import ru.skillbox.socialnetwork.service.NotificationService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@Api(tags = "Notifications(Уведомления)")
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService notificationService;

    @Override
    @ApiOperation(value = "Получение уведомлений пользователя")
    public ResponseEntity<NotificationResponse> getNotifications(@RequestParam(value = "offset", defaultValue = "0") String offset,
                                                                 @RequestParam(value = "itemPerPage", defaultValue = "20") String itemPerPage,
                                                                 Principal principal) {
        return ResponseEntity.ok(notificationService.getNotifications(offset, itemPerPage, principal));
    }

    @Override
    @ApiOperation(value = "Удаление уведомлений пользователя")
    public ResponseEntity<NotificationResponse> putNotifications(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                                                                @RequestParam(value = "all", required = false, defaultValue = "false") Boolean deleteAllNotifications,
                                                                Principal principal) {
        return ResponseEntity.ok(notificationService.putNotifications(id, deleteAllNotifications, principal));
    }
}
