package ru.skillbox.socialnetwork.service.impl;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.message.DialogResponse;
import ru.skillbox.socialnetwork.data.entity.Message;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.ReadStatus;
import ru.skillbox.socialnetwork.data.repository.FriendshipRepository;
import ru.skillbox.socialnetwork.data.repository.MessageRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.exception.DialogNotFoundException;
import ru.skillbox.socialnetwork.exception.PersonNotFoundException;
import ru.skillbox.socialnetwork.service.DialogService;

@Service
public class DialogServiceImpl implements DialogService {

    private final MessageRepository messageRepository;
    private final PersonRepo personRepository;
    private final FriendshipRepository friendshipRepository;

    public DialogServiceImpl(MessageRepository messageRepository, PersonRepo personRepository, FriendshipRepository friendshipRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public DialogResponse sendMessage(String message) {
        return null;
    }

    @Override
    public DialogResponse getAllMessages(Long id) {
        Message message = findMessageById(id);
        return DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .total(1)
                .offset(1)
                .perPage(10)
                .data(DialogResponse.Data.builder()
                        .id(message.getId())
                        .time(message.getTime())
                        .author(message.getAuthor().getId())
                        .recipientId(message.getRecipient().getId())
                        .messageText(message.getText())
                        .readStatus(ReadStatus.SENT)
                        .build())
                .build();
    }

    @Override
    public DialogResponse dialogCreate(Long userId) {
        Person recipient = findPersonById(userId);
        Message message = new Message();
        message.setRecipient(recipient);
        messageRepository.save(message);
        return DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(message.getId())
                        .build())
                .build();
    }

    private Message findMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new DialogNotFoundException("Dialog not found"));
    }
    private Person findPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
    }
}
