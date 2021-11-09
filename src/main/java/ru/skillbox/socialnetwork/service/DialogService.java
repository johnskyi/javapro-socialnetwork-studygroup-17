package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.message.DialogResponse;

import java.security.Principal;


public interface DialogService {

    DialogResponse sendMessage(String message);

    DialogResponse getAllMessages(Long id);

    DialogResponse dialogCreate(Long userId, Principal principal);
}
