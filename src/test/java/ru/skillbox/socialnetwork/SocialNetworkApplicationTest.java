package ru.skillbox.socialnetwork;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SocialNetworkApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Тестируем старт приложения и наличие контекста")
    public void contextLoads() throws InterruptedException {
        Thread.sleep(5000);
        assertNotNull(context);
    }

}