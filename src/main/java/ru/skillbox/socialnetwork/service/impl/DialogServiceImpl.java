package ru.skillbox.socialnetwork.service.impl;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogRequest;
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
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
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
    public DialogResponse sendMessage(DialogRequest request, Principal principal) {
        Person author = findPersonByEmail(principal.getName());
        Dialog dialog = findDialogById(request.getDialogId());
        Message newMessage = Message.builder()
                .dialog(dialog)
                .author(author)
                .recipient(dialog.getRecipient())
                .time(LocalDateTime.now())
                .readStatus(ReadStatus.SENT)
                .text(request.getMessage())
                .build();
        messageRepository.save(newMessage);
        dialog.getMessages().add(newMessage);
        dialogRepository.save(dialog);
        return DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(dialog.getId())
                        .time(LocalDateTime.now())
                        .author(author.getId())
                        .recipientId(dialog.getRecipient().getId())
                        .messageText(request.getMessage())
                        .readStatus(ReadStatus.SENT)
                        .build())
                .build();
    }

    @Override
    public DialogResponse getAllMessages(Long dialogId) {
        Dialog dialog = findDialogById(dialogId);
        List<Message> messages = dialog.getMessages();
        return DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .total(dialog.getMessages().size())
                .data(DialogResponse.Data.builder()
                        .id(dialog.getId())
                        .author(dialog.getAuthor().getId())
                        .recipientId(dialog.getRecipient().getId())
                        .messageText(messages.toString())
                        .readStatus(ReadStatus.READ)
                        .build())
                .build();
    }

    @Override
    public DialogResponse dialogCreate(Long userId, Principal principal) {
        Person author = findPersonByEmail(principal.getName());
        Person recipient = findPersonById(userId);
        Dialog dialog;
        if(checkDialogByRecipient(author,recipient)) {
            dialog = findDialogByParticipants(author, recipient);
        } else {
            dialog = new Dialog();
            dialog.setRecipient(recipient);
            dialog.setAuthor(author);
            dialog = dialogRepository.save(dialog);
        }
            return DialogResponse.builder()
                    .timestamp(System.currentTimeMillis())
                    .data(DialogResponse.Data.builder()
                            .id(dialog.getId())
                            .build())
                    .build();

    }

    @Override
    public DialogResponse dialogDelete(Long dialogId, Principal principal) {
        findPersonByEmail(principal.getName());
        Dialog dialog = findDialogById(dialogId);
        dialogRepository.delete(dialog);
        return DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(dialogId)
                        .build())
                .build();
    }

    @Override
    public DialogResponse messageDelete(Long dialogId, Long messageId, Principal principal) {
        findPersonByEmail(principal.getName());
        Dialog dialog = findDialogById(dialogId);
        Message message = findMessageById(messageId);
        messageRepository.delete(message);
        dialog.getMessages().remove(message);
        dialogRepository.save(dialog);
        return DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(messageId)
                        .build())
                .build();
    }

    @Override
    public DialogResponse getDialog(Long dialogId) {
        Dialog dialog = findDialogById(dialogId);
        return DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(dialog.getId())
                        .build())
                .build();
    }

    @Override
    public DialogResponse getAllDialogs(Principal principal) {
        String authorEmail = principal.getName();
        Person author = findPersonByEmail(authorEmail);
        List<Dialog> dialogs = dialogRepository.findAllByAuthorOrRecipient(author, author);
        if(dialogs.isEmpty()) {
            throw new DialogNotFoundException("Dialogs list is empty");
        }
        return DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .dialogs(dialogs)
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
                .orElseThrow(() -> new PersonNotAuthorized("Sorry you are not authorized"));
    }
    private Boolean checkDialogByRecipient(Person author, Person recipient) {
       return   dialogRepository.findByAuthorAndRecipient(author, recipient).isPresent();
    }
    private Dialog findDialogByParticipants(Person author, Person recipient) {
        return dialogRepository.findByAuthorAndRecipient(author, recipient).orElseThrow(() -> new DialogNotFoundException("Dialog not found"));
    }
}
