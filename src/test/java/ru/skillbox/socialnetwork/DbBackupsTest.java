package ru.skillbox.socialnetwork;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skillbox.socialnetwork.service.GoogleDriveService;
import ru.skillbox.socialnetwork.utils.BackupGoogleDriveTask;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class DbBackupsTest {

    private static final Logger log = LoggerFactory.getLogger(DbBackupsTest.class);
    private static BackupGoogleDriveTask databaseBackupCreateTask;
    private static final DateTimeFormatter fileNameDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private static String testFileName = LocalDateTime.now().format(fileNameDateFormat) + ".test";
    private static String testFolderId = "1-_KEk819bSMEB81Kjr1EzPslKqBCfDto";

    @BeforeAll
    static void init() {

        databaseBackupCreateTask = new BackupGoogleDriveTask(new GoogleDriveService());

        databaseBackupCreateTask.setLocalPath("test/");

        File localFolder = new File(databaseBackupCreateTask.getLocalPath());
        log.info("Folder for tests: " + localFolder.getAbsolutePath());

        if(!localFolder.exists()){
            try {
                FileUtils.forceMkdir(localFolder);
            } catch (IOException ex) {
                log.info("Cannot create folder: " + localFolder.getAbsolutePath());
                ex.printStackTrace();
                return;
            }
        }

        try(PrintWriter writer = new PrintWriter(localFolder + "/" + testFileName)){
            writer.println("Test File for javapro");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Проверка метода загрузки файлов на гугл драйв")
    public void googleDriveLoadTest(){

        databaseBackupCreateTask.setLocalPath("test/");

        File file = new File(databaseBackupCreateTask.getLocalPath() + testFileName);

        assertTrue(file.exists());
        assertTrue(databaseBackupCreateTask.loadFileToGoogleDrive(file.getName(),file.getName(), testFolderId));
    }

    @Test
    @DisplayName("Проверка таска по бэкапам")
    public void googleDriveTaskTest(){

        databaseBackupCreateTask.setLocalPath("/home/gitlab-runner/backup/");

        databaseBackupCreateTask.copyDataToGoogleDrive();

    }


    @AfterAll
    static void afterAllTests(){
        File folder = new File("test/");
        if (folder.exists()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                file.delete();
            }
            folder.delete();
        }
    }
}
