package ru.skillbox.socialnetwork;

import com.google.api.client.http.FileContent;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skillbox.socialnetwork.service.GoogleDriveService;
import ru.skillbox.socialnetwork.utils.BackupGoogleDriveTask;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DbBackupsTest {

    @SpyBean
    BackupGoogleDriveTask databaseBackupCreateTask;
    @MockBean
    GoogleDriveService googleDriveService;

    @BeforeEach
     void setUp() {
    }

    @Test
    @DisplayName("Проверка метода загрузки файлов на гугл драйв")
    public void googleDriveLoadTest() throws Exception {

        Mockito.when(googleDriveService.uploadFile(Mockito.any(FileContent.class),Mockito.any(String.class),Mockito.any())).thenReturn("123");
        assertTrue(databaseBackupCreateTask.loadFileToGoogleDrive(Mockito.any(String.class),Mockito.any(String.class), Mockito.any()));
    }

}
