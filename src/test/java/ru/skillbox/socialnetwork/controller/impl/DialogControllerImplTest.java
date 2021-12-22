package ru.skillbox.socialnetwork.controller.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogRequest;
import ru.skillbox.socialnetwork.data.dto.dialogs.DialogResponse;
import ru.skillbox.socialnetwork.data.entity.Dialog;
import ru.skillbox.socialnetwork.data.entity.Message;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.ReadStatus;
import ru.skillbox.socialnetwork.exception.DialogNotFoundException;
import ru.skillbox.socialnetwork.exception.MessageNotFoundException;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.service.impl.DialogServiceImpl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DialogControllerImplTest {


    private static Person personOne;
    private static Person personTwo;
    private static Dialog dialog;
    private static List<Dialog> testDialogs;
    private static DialogRequest dialogRequest;

    @Autowired
    private DialogControllerImpl dialogController;
    @MockBean
    private DialogServiceImpl dialogService;
    @MockBean
    private Principal principal;

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
        testDialogs = new ArrayList<>();
        testDialogs.add(dialog);
    }
    @BeforeAll
    static void initDialogRequest() {
        dialogRequest = DialogRequest.builder()
                .dialogId(1L)
                .message("Hi")
                .build();
    }
    @Test
    @DisplayName("Send message")
    void sendMessage() {
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
    @DisplayName("Get list messages")
    void getAllMessages() {
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
    @DisplayName("Dialog create")
    void dialogCreate() {
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
    @DisplayName("Delete dialog")
    void dialogDelete() {
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
    @DisplayName("Delete message")
    void messageDelete() {
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
    @Test
    @DisplayName("Get all dialogs")
    void getAllDialogs() {
        DialogResponse dialogResponse = DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .dialogs(testDialogs)
                        .build())
                .build();
        when(dialogService.getAllDialogs(any())).thenReturn(dialogResponse);
        assertEquals(ResponseEntity.ok(dialogResponse),dialogController.getAllDialogs(principal));
    }
    @Test
    @DisplayName("Get dialog by Id")
    void getDialog() {
        DialogResponse dialogResponse = DialogResponse.builder()
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.Data.builder()
                        .id(1L)
                        .build())
                .build();
        when(dialogService.getDialog(1L)).thenReturn(dialogResponse);
        assertEquals(ResponseEntity.ok(dialogResponse),dialogController.getDialog(1L));
    }
    @Test
    @DisplayName("Get dialog unknown id")
    public void getDialog_unknownId_throwsDialogNotFound() {
        when(dialogService.getDialog(any())).thenThrow(DialogNotFoundException.class);
        assertThrows(DialogNotFoundException.class, () -> dialogController.getDialog(any()));
    }
    @Test
    @DisplayName("Get dialogs not authorized person")
    public void getDialogs_notAuthorized_throwsPersonNotAuthorized() {
        when(dialogService.getAllDialogs(any())).thenThrow(PersonNotAuthorized.class);
        assertThrows(PersonNotAuthorized.class, () -> dialogController.getAllDialogs(any()));
    }


    @Test
    @DisplayName("Set message  person unauthorized throws PersonNotAuthorized")
    public void sendMessage_notAuthorizedUser_throwsPersonNotAuthorized() {
        when(dialogService.sendMessage(any(),any())).thenThrow(PersonNotAuthorized.class);
       assertThrows(PersonNotAuthorized.class, () -> dialogController.sendMessage(dialogRequest,principal));
    }
    @Test
    @DisplayName("Delete  message unauthorized throws PersonNonAuthorized")
    public void deleteMessage_notAuthorizedUser_throwsPersonNotAuthorized() {
        when(dialogService.messageDelete(any(),any(),any())).thenThrow(PersonNotAuthorized.class);
        assertThrows(PersonNotAuthorized.class, () -> dialogController.messageDelete(dialog.getId(),dialog.getMessages().get(0).getId(),principal));
    }
    @Test
    @DisplayName("Delete dialog  person unauthorized throws PersonNonAuthorized")
    public void deleteDialog_notAuthorizedUser_throwsPersonNotAuthorized() {
        when(dialogService.dialogDelete(any(),any())).thenThrow(PersonNotAuthorized.class);
        assertThrows(PersonNotAuthorized.class, () -> dialogController.dialogDelete(dialog.getId(),principal));
    }
    @Test
    @DisplayName("Create dialog  person unauthorized throws PersonNonAuthorized")
    public void createDialog_notAuthorizedUser_throwsPersonNotAuthorized() {
        when(dialogService.dialogCreate(any(Long.class),any(Principal.class))).thenThrow(PersonNotAuthorized.class);
        assertThrows(PersonNotAuthorized.class, () -> dialogController.dialogCreate(personOne.getId(),principal));
    }
    @Test
    @DisplayName("Get all message unknown dialog throws DialogNotFound")
    void getAllMessage_unknownDialog_throwDialogNotFoundException() {
        when(dialogService.getAllMessages(any(Long.class))).thenThrow(DialogNotFoundException.class);
        assertThrows(DialogNotFoundException.class, () -> dialogController.getAllMessages(3L));
    }
    @Test
    @DisplayName("Delete unknown message throw MessageNotFound")
    void deleteMessage_unknownMessage_throwMessageNotFoundException() {
        when(dialogService.messageDelete(any(Long.class), any(Long.class), any(Principal.class))).thenThrow(MessageNotFoundException.class);
        assertThrows(MessageNotFoundException.class, () -> dialogController.messageDelete(1L, 1L, principal));
    }
    @Test
    @DisplayName("Delete unknown dialog throw DialogNotFound")
    void deleteDialog_unknownDialog_throwDialogNotFoundException() {
        when(dialogService.dialogDelete(any(Long.class),any(Principal.class))).thenThrow(DialogNotFoundException.class);
        assertThrows(DialogNotFoundException.class, () -> dialogController.dialogDelete(1L, principal));
    }
    @Test
    @DisplayName("Delete message unknown dialog throw DialogNotFound")
    void deleteMessage_unknownDialog_throwDialogNotFoundException() {
        when(dialogService.messageDelete(any(Long.class),any(Long.class),any(Principal.class))).thenThrow(DialogNotFoundException.class);
        assertThrows(DialogNotFoundException.class, () -> dialogController.messageDelete(1L,1L, principal));
    }
    }