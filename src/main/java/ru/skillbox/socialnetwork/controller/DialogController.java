package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogRequest;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogResponse;

import java.security.Principal;

public interface DialogController {

    /**Отправка сообщений.*/
    @PostMapping("/api/v1/dialogs/messages")
    ResponseEntity<DialogResponse> sendMessage(@RequestBody DialogRequest request, Principal principal);

    /**Получить список сообщений в диалоге.*/
    @GetMapping("/api/v1/dialogs/{id}/messages")
    ResponseEntity<DialogResponse> getAllMessages(@PathVariable Long dialogId);

    /**Создать диалог.*/
    @PostMapping("/api/v1/dialogs")
    ResponseEntity<DialogResponse> dialogCreate(@RequestParam("userId") Long userId,Principal principal);

    /**Delete dialog by Id.*/
    @DeleteMapping("/api/v1/dialogs/")
    ResponseEntity<DialogResponse> dialogDelete(@RequestParam("dialogId") Long dialogId, Principal principal);

    /**Delete message by Id.*/
    @DeleteMapping("/api/v1/dialogs/{dialogId}/messages/")
    ResponseEntity<DialogResponse> messageDelete(@PathVariable Long dialogId,
                                                 @RequestParam("messageId") Long messageId, Principal principal);
    /**Get all dialogs.*/
    @GetMapping("/api/v1/dialogs/")
    ResponseEntity<DialogResponse> getAllDialogs(Principal principal);

    /**Get dialog by id.*/
    @GetMapping("api/v1/dialog/")
    ResponseEntity<DialogResponse> getDialog(@RequestParam("dialogId") Long dialogId);


}
