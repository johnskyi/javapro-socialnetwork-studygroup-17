package ru.skillbox.socialnetwork.controller.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogRequest;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogResponse;
import ru.skillbox.socialnetwork.data.entity.Dialog;
import ru.skillbox.socialnetwork.data.entity.Message;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.ReadStatus;
import ru.skillbox.socialnetwork.data.repository.DialogRepository;
import ru.skillbox.socialnetwork.data.repository.MessageRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.service.impl.DialogServiceImpl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DialogControllerImplTest {


    private static Person personOne;
    private static Person personTwo;
    private static Dialog dialog;
    private static DialogRequest dialogRequest;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DialogControllerImpl dialogController;
    @MockBean
    private DialogServiceImpl dialogService;
    @MockBean
    private PersonRepo personRepo;
    @MockBean
    private Principal principal;
    @MockBean
    private DialogRepository dialogRepository;
    @MockBean
    private MessageRepository messageRepository;

    @BeforeAll
    static void initPersonOne() {
        personOne = new Person();
        personOne.setId(1L);
        personOne.setFirstName("Петр");
        personOne.setLastName("Петров");
        personOne.setEmail("test2@test2.com");
    }
    @BeforeAll
    static void initPersonTwo() {
        personTwo = new Person();
        personTwo.setId(2L);
        personTwo.setFirstName("Иван Иванов");
        personTwo.setLastName("Петров");
        personTwo.setEmail("test3@test3.com");
    }
    @BeforeAll
    static void initDialog() {
        dialog = new Dialog();
        dialog.setId(1L);
        dialog.setAuthor(personOne);
        dialog.setRecipient(personTwo);
        Message messageOne = Message.builder()
                .dialog(dialog)
                .author(personOne)
                .recipient(personTwo)
                .id(1L)
                .time(LocalDateTime.of(2021,12,10,10,10))
                .readStatus(ReadStatus.SENT)
                .text("Hi")
                .build();
        dialog.setMessages(List.of(messageOne));
    }
    @BeforeAll
    static void initDialogRequest() {
        dialogRequest = DialogRequest.builder()
                .dialogId(1L)
                .message("Hi")
                .build();
    }
    @Test
    @DisplayName("Отправка сообщения")
    void sendMessage() {
        when(dialogRepository.findById(any())).thenReturn(Optional.ofNullable(dialog));
        when(personRepo.findByEmail(any())).thenReturn(Optional.ofNullable(personOne));
        DialogResponse dialogResponseSend = DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(1L)
                        .time(LocalDateTime.now())
                        .author(1L)
                        .recipientId(2L)
                        .messageText("Hi")
                        .readStatus(ReadStatus.SENT)
                        .build())
                .build();
        when(dialogService.sendMessage(dialogRequest,principal)).thenReturn(dialogResponseSend);

        assertEquals(ResponseEntity.ok(dialogResponseSend),dialogController.sendMessage(dialogRequest,principal));
        verify(dialogService,times(1)).sendMessage(dialogRequest,principal);

    }

    @Test
    @DisplayName("Получение списка сообщений")
    void getAllMessages() {
        when(dialogRepository.findById(any())).thenReturn(Optional.ofNullable(dialog));
        DialogResponse dialogResponseGetAll =  DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .total(dialog.getMessages().size())
                .offset(1)
                .perPage(10)
                .data(DialogResponse.Data.builder()
                        .id(1L)
                        .author(1L)
                        .recipientId(2L)
                        .messageText(dialog.getMessages().get(0).toString())
                        .readStatus(ReadStatus.SENT)
                        .build())
                .build();
        when(dialogService.getAllMessages(1L)).thenReturn(dialogResponseGetAll);

        assertEquals(ResponseEntity.ok(dialogResponseGetAll),dialogController.getAllMessages(1L));
        verify(dialogService,times(1)).getAllMessages(1L);
    }

    @Test
    @DisplayName("Создание диалога")
    void dialogCreate() {
        when(personRepo.findById(any())).thenReturn(Optional.ofNullable(personTwo));
        when(principal.getName()).thenReturn(personOne.getEmail());
        DialogResponse dialogResponseCreate =  DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(1L)
                        .build())
                .build();
        when(dialogService.dialogCreate(2L,principal)).thenReturn(dialogResponseCreate);

        assertEquals(ResponseEntity.ok(dialogResponseCreate), dialogController.dialogCreate(2L,principal));
        verify(dialogService, times(1)).dialogCreate(2L,principal);

    }

    @Test
    @DisplayName("Удаление диалога")
    void dialogDelete() {
        when(dialogRepository.findById(any())).thenReturn(Optional.ofNullable(dialog));
        when(personRepo.findById(any())).thenReturn(Optional.ofNullable(personOne));
        when(principal.getName()).thenReturn(personOne.getEmail());
        DialogResponse dialogResponseDelete =  DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(1L)
                        .build())
                .build();
        when(dialogService.dialogDelete(1L,principal)).thenReturn(dialogResponseDelete);

        assertEquals(ResponseEntity.ok(dialogResponseDelete),dialogController.dialogDelete(1L,principal));
        verify(dialogService,times(1)).dialogDelete(1L,principal);



    }

    @Test
    @DisplayName("Удаление сообщений")
    void messageDelete() {
        when(dialogRepository.findById(any())).thenReturn(Optional.ofNullable(dialog));
        when(personRepo.findById(any())).thenReturn(Optional.ofNullable(personOne));
        when(principal.getName()).thenReturn(personOne.getEmail());
        when(messageRepository.findById(1L)).thenReturn(Optional.ofNullable(dialog.getMessages().get(0)));
        DialogResponse dialogResponseDelete = DialogResponse.builder()
                .error("string")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(1L)
                        .build())
                .build();
        when(dialogService.messageDelete(1L,1L,principal)).thenReturn(dialogResponseDelete);

        assertEquals(ResponseEntity.ok(dialogResponseDelete),dialogController.messageDelete(1L,1L,principal));
        verify(dialogService,times(1)).messageDelete(1L,1L,principal);
    }
}