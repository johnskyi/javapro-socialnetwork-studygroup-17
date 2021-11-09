package ru.skillbox.socialnetwork.service.impl;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.message.DialogResponse;
import ru.skillbox.socialnetwork.data.entity.Dialog;
import ru.skillbox.socialnetwork.data.entity.Message;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.ReadStatus;
import ru.skillbox.socialnetwork.data.repository.DialogRepository;
import ru.skillbox.socialnetwork.data.repository.MessageRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.exception.DialogNotFoundException;
import ru.skillbox.socialnetwork.exception.PersonNotFoundException;
import ru.skillbox.socialnetwork.service.DialogService;

import java.security.Principal;
import java.util.List;

@Service
public class DialogServiceImpl implements DialogService {

    private final MessageRepository messageRepository;
    private final PersonRepo personRepository;
    private final DialogRepository dialogRepository;

    public DialogServiceImpl(MessageRepository messageRepository, PersonRepo personRepository,DialogRepository dialogRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
        this.dialogRepository = dialogRepository;
    }

    @Override
    public DialogResponse sendMessage(String message) {
        return null;
    }

    @Override
    public DialogResponse getAllMessages(Long id) {
        Dialog dialog = findDialogById(id);
        List<Message> messages = dialog.getMessages();
        return DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .total(dialog.getMessages().size())
                .offset(1)
                .perPage(10)
                .data(DialogResponse.Data.builder()
                        .id(dialog.getId())
                        .author(dialog.getAuthor().getId())
                        .recipientId(dialog.getRecipient().getId())
                        .messageText(messages.toString())
                        .readStatus(ReadStatus.SENT)
                        .build())
                .build();
    }

    @Override
    public DialogResponse dialogCreate(Long userId, Principal principal) {
        Person recipient = findPersonById(userId);
        Person author = findPersonByEmail(principal.getName());
        Dialog dialog = new Dialog();
        dialog.setRecipient(recipient);
        dialog.setAuthor(author);
        dialogRepository.save(dialog);
        return DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(dialog.getId())
                        .build())
                .build();
    }
    private Dialog findDialogById(Long id) {
        return dialogRepository.findById(id)
                .orElseThrow(() -> new DialogNotFoundException("Dialog not found"));
    }
    private Person findPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
    }
    private Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
    }
}
