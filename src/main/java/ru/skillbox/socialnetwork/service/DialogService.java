package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.message.DialogResponse;

public interface DialogService {

    DialogResponse sendMessage(String message);

    DialogResponse getAllMessages(Long id);

    DialogResponse dialogCreate(Long userId);
}
