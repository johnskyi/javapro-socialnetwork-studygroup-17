package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogResponse;

import java.security.Principal;

public interface DialogController {

    /**Отправка сообщений.*/
    @PostMapping("/api/v1/dialogs/messages")
    ResponseEntity<DialogResponse> sendMessage(@RequestBody  Principal principal,
                                                Long dialogId,
                                                String message);
    /**Получить список сообщений в диалоге.*/
    @GetMapping("/api/v1/dialogs/{id}/messages")
    ResponseEntity<DialogResponse> getAllMessages(@RequestParam("dialogId") Long dialogId);

    /**Создать диалог.*/
    @PostMapping("/api/v1/dialogs")
    ResponseEntity<DialogResponse> dialogCreate(@RequestParam("userId") Long userId, Principal principal);

    /**Delete dialog by Id.*/
    @DeleteMapping("/api/v1/dialogs/")
    ResponseEntity<DialogResponse> dialogDelete(@RequestParam("dialogId") Long dialogId, Principal principal);

    /**Delete message by Id.*/
    @DeleteMapping("/api/v1/dialogs/{dialogId}/messages/{messageId}")
    ResponseEntity<DialogResponse> messageDelete(@RequestParam("dialogId") Long dialogId,
                                                 @RequestParam("messageId") Long messageId,
                                                 Principal principal);


}
