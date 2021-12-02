package ru.skillbox.socialnetwork.service.impl;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogResponse;
import ru.skillbox.socialnetwork.data.entity.Dialog;
import ru.skillbox.socialnetwork.data.entity.Message;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.ReadStatus;
import ru.skillbox.socialnetwork.data.repository.DialogRepository;
import ru.skillbox.socialnetwork.data.repository.MessageRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.exception.DialogNotFoundException;
import ru.skillbox.socialnetwork.exception.MessageNotFoundException;
import ru.skillbox.socialnetwork.exception.PersonNotFoundException;
import ru.skillbox.socialnetwork.service.DialogService;

import java.security.Principal;
import java.time.LocalDateTime;
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
    public DialogResponse sendMessage(Principal principal, Long dialogId, String message) {
        Dialog dialog = findDialogById(dialogId);
        Person author = findPersonByEmail(principal.getName());
        Message newMessage = Message.builder()
                .dialog(dialog)
                .author(author)
                .time(LocalDateTime.now())
                .readStatus(ReadStatus.SENT)
                .text(message)
                .build();
        messageRepository.save(newMessage);
        dialog.getMessages().add(newMessage);
        dialogRepository.save(dialog);
        return DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(dialog.getId())
                        .time(LocalDateTime.now())
                        .author(author.getId())
                        .recipientId(dialog.getRecipient().getId())
                        .messageText(message)
                        .readStatus(ReadStatus.SENT)
                        .build())
                .build();
    }

    @Override
    public DialogResponse getAllMessages(Long dialogId) {
        Dialog dialog = findDialogById(dialogId);
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

    @Override
    public DialogResponse dialogDelete(Long dialogId, Principal principal) {
        Dialog dialog = findDialogById(dialogId);
        findPersonByEmail(principal.getName());
        dialogRepository.delete(dialog);
        return DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(dialogId)
                        .build())
                .build();
    }

    @Override
    public DialogResponse messageDelete(Long dialogId, Long messageId, Principal principal) {
        Dialog dialog = findDialogById(dialogId);
        findPersonByEmail(principal.getName());
        Message message = findMessageById(messageId);
        messageRepository.delete(message);
        dialog.getMessages().remove(message);
        dialogRepository.save(dialog);
        return DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(messageId)
                        .build())
                .build();
    }

    private Dialog findDialogById(Long id) {
        return dialogRepository.findById(id)
                .orElseThrow(() -> new DialogNotFoundException("Dialog not found"));
    }
    private Message findMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message not found"));
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
