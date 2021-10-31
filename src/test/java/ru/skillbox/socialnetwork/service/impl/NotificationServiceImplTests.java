//package ru.skillbox.socialnetwork.service.impl;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.skillbox.socialnetwork.data.entity.Notification;
//import ru.skillbox.socialnetwork.data.entity.Person;
//import ru.skillbox.socialnetwork.data.repository.NotificationRepository;
//import ru.skillbox.socialnetwork.data.repository.PersonRepo;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "application.yaml")
//@AutoConfigureMockMvc
//class NotificationServiceImplTests {
////
////    @MockBean
////    private final PersonRepo personRepositoryMock;
////    @MockBean
////    private final NotificationRepository notificationRepositoryMock;
////    @MockBean
////    @Autowired
////    private final NotificationServiceImpl notificationService;
////    @Autowired
////    private MockMvc mockMvc;
////
////    private Principal principal;
////    private Person person;
////    private Notification notification;
////
////    @Autowired
////    NotificationServiceImplTests(PersonRepo personRepository, NotificationRepository notificationRepository, NotificationServiceImpl notificationService) {
////        this.personRepositoryMock = personRepository;
////        this.notificationRepositoryMock = notificationRepository;
////        this.notificationService = notificationService;
////    }
////
////    @BeforeEach
////    void setUp() {
////        person = new Person();
////        person.setEmail(principal.getName());
////        person.setId(1L);
////        person.setFirstName("TestFirstName");
////        person.setLastName("TestLastName");
////        notification = new Notification();
////        notification.setId(1L);
////        notification.setPerson(person);
////        person.setNotifications(List.of(notification));
////    }
////
////    @AfterEach
////    void tearDown() {
////    }
////
////    @Test
////    void getNotifications() throws Exception {
////        Mockito.when(notificationRepositoryMock.findAllByPersonId(anyLong())).thenReturn(person.getNotifications());
////        Mockito.when(personRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(person));
////        Iterable<Notification> notifications = notificationRepositoryMock.findAllByPersonId(person.getId());
////        Assertions.assertNotNull(notifications);
////
////        this.mockMvc.perform(
////                get("/api/v1/notification")
////                        .param("offset", "1")
////                        .param("itemPerPage", "1")
////                        .principal(principal)
////        ).andReturn();
////    }
////
////    @Test
////    void putNotifications() {
////    }
//}