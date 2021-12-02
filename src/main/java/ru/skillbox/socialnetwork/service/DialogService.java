package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.dialogs.DialogResponse;

import java.security.Principal;


public interface DialogService {

    DialogResponse sendMessage(Principal principal, Long dialogId,String message);

    DialogResponse getAllMessages(Long dialogId);

    DialogResponse dialogCreate(Long userId, Principal principal);

    DialogResponse dialogDelete(Long dialogId, Principal principal);

    DialogResponse messageDelete(Long dialogId, Long messageId, Principal principal);
}
