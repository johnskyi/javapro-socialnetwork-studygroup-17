package ru.skillbox.socialnetwork.controller.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import ru.skillbox.socialnetwork.BaseTestObjects;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.service.impl.PasswordRecoveryServiceImpl;

@SpringBootTest(properties = "application.yaml", classes = {PasswordRecoveryServiceImpl.class, PasswordRecoveryControllerImpl.class})
class PasswordRecoveryControllerImplTest extends BaseTestObjects {

    @MockBean
    private  PasswordRecoveryServiceImpl passwordRecoveryService;

    @MockBean
    private  JavaMailSender javaMailSender;

    @MockBean
    private  PersonRepo personRepo;

    @Autowired
    private PasswordRecoveryControllerImpl passwordRecoveryController;

    @BeforeAll
    @Override
    public void setUp(){
        super.setUp();

    }


    @Test
    void sendEmail() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void setEmail() {
    }
}