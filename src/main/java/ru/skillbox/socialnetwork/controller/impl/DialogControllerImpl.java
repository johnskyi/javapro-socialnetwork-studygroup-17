package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.DialogController;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogRequest;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogResponse;
import ru.skillbox.socialnetwork.service.impl.DialogServiceImpl;

import java.security.Principal;

@RestController
@Api(tags = "Работа с диалогами")
@Slf4j
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

    private final DialogServiceImpl dialogService;
    private final Principal principal;


    @Override
    @ApiOperation(value="Отправка сообщений")
    public ResponseEntity<DialogResponse> sendMessage(DialogRequest request) {
        log.info("POST /api/v1/dialogs/messages" + request.getMessage());
        return ResponseEntity.ok(dialogService.sendMessage(request, principal));
    }

    @Override
    @ApiOperation(value="Получение списка сообщений")
    public ResponseEntity<DialogResponse> getAllMessages(Long dialogId) {
        log.info("GET /api/v1/dialogs/{id}/messages" + dialogId);
        return ResponseEntity.ok(dialogService.getAllMessages(dialogId));
    }

    @Override
    @ApiOperation(value="Создание диалога")
    public ResponseEntity<DialogResponse> dialogCreate(Long userId) {
        log.info("POST /api/v1/dialogs" + userId);
        return ResponseEntity.ok(dialogService.dialogCreate(userId, principal));
    }

    @Override
    @ApiOperation(value="Удаление диалога")
    public ResponseEntity<DialogResponse> dialogDelete(Long dialogId) {
        log.info("DELETE /api/v1/dialogs/" + dialogId);
        return ResponseEntity.ok(dialogService.dialogDelete(dialogId, principal));
    }

    @Override
    @ApiOperation(value="Удаление сообщения")
    public ResponseEntity<DialogResponse> messageDelete(Long dialogId, Long messageId) {
        log.info("DELETE /api/v1/dialogs/messages" + dialogId + " -- " +  messageId);
        return ResponseEntity.ok(dialogService.messageDelete(dialogId,messageId, principal));
    }
}
