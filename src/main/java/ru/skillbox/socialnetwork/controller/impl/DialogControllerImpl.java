package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.DialogController;
import ru.skillbox.socialnetwork.data.dto.message.DialogResponse;
import ru.skillbox.socialnetwork.service.DialogService;
import ru.skillbox.socialnetwork.service.impl.DialogServiceImpl;

import java.security.Principal;

@RestController
@Api(tags = "Работа с диалогами")
@Slf4j
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

    private final DialogServiceImpl dialogService;


    @Override
    @ApiOperation(value="Отправка сообщений")
    public ResponseEntity<DialogResponse> sendMessage(String message) {
        log.info("POST /api/v1/dialogs/{id}/messages" + message);
        return ResponseEntity.ok(dialogService.sendMessage(message));
    }

    @Override
    @ApiOperation(value="Получение списка сообщений")
    public ResponseEntity<DialogResponse> getAllMessages(Long id) {
        log.info("GET /api/v1/dialogs/{id}/messages" + id);
        return ResponseEntity.ok(dialogService.getAllMessages(id));
    }

    @Override
    @ApiOperation(value="Создание диалога")
    public ResponseEntity<DialogResponse> dialogCreate(Long userId, Principal principal) {
        log.info("POST /api/v1/dialogs" + userId);
        return ResponseEntity.ok(dialogService.dialogCreate(userId, principal));
    }
}
