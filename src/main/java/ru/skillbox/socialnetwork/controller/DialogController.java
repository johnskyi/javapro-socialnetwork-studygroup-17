package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.socialnetwork.data.dto.message.DialogResponse;

public interface DialogController {

    /**Отправка сообщений.*/
    @PostMapping("/api/v1/dialogs/{id}/messages")
    ResponseEntity<DialogResponse> sendMessage(@RequestBody String message);
    /**Получить список сообщений в диалоге.*/
    @GetMapping("/api/v1/dialogs/{id}/messages")
    ResponseEntity<DialogResponse> getAllMessages(@RequestParam("dialog_id") Long id);
    /**Создать диалог.*/
    @PostMapping("/api/v1/dialogs")
    ResponseEntity<DialogResponse> dialogCreate(@RequestParam("user_ids") Long id);


}
