package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.message.DialogResponse;

import java.security.Principal;


public interface DialogService {

    DialogResponse sendMessage(Principal principal, Long dialogId,String message);

    DialogResponse getAllMessages(Long dialogId);

    DialogResponse dialogCreate(Long userId, Principal principal);
}
