package ru.skillbox.socialnetwork;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.skillbox.socialnetwork.service.GoogleDriveService;
import ru.skillbox.socialnetwork.utils.DatabaseBackupCreateTask;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

public class DbBackupsTest {

    private static DatabaseBackupCreateTask databaseBackupCreateTask;

    @BeforeAll
    static void init() {
        databaseBackupCreateTask = new DatabaseBackupCreateTask(new GoogleDriveService());

        databaseBackupCreateTask.setLocalPath("/home/alexey/db_backup/");
        databaseBackupCreateTask.setHost("45.134.255.54");
        databaseBackupCreateTask.setMaxTotalFilesSize(Long.MAX_VALUE);
        databaseBackupCreateTask.setMinFreeSpace(0);
        databaseBackupCreateTask.setMaxFilesCount(Long.MAX_VALUE);

    }

    @Test
    @Disabled
    @DisplayName("Проверка переполнения файлов бэкапа по minimal free space")
    public void checkTooManyBackupFilesMinFreeSpaceTest() {

        File folder = new File(databaseBackupCreateTask.getLocalPath());
        long freeSpace = folder.getUsableSpace();

        databaseBackupCreateTask.setMinFreeSpace(freeSpace + 1);
        assertTrue(databaseBackupCreateTask.checkTooManyBackups(folder));

        databaseBackupCreateTask.setMinFreeSpace(freeSpace - 1);
        assertFalse(databaseBackupCreateTask.checkTooManyBackups(folder));

    }

    @Test
    @Disabled
    @DisplayName("Проверка создания папки")
    public void createFolderTest() throws IOException {

        File folder = new File(databaseBackupCreateTask.getLocalPath());

        System.out.println("Folder name: " + folder.getPath());

        if (folder.exists()) {
            FileUtils.forceDelete(folder);
        }

        assertFalse(folder.exists());

        databaseBackupCreateTask.createBackupAndMoveToGoogleDrive();

        assertTrue(folder.exists());

    }

    @Test
    @Disabled
    @DisplayName("Проверка переполнения файлов бэкапа по превышению количества файлов")
    public void checkTooManyBackupFilesMaxCountTest() {

        File folder = new File(databaseBackupCreateTask.getLocalPath());

        databaseBackupCreateTask.setMaxFilesCount(1);

        for (int i = 0; i <= databaseBackupCreateTask.getMaxFilesCount(); i++) {
            databaseBackupCreateTask.createBackupAndMoveToGoogleDrive();
        }

        assertTrue(databaseBackupCreateTask.checkTooManyBackups(folder));

        databaseBackupCreateTask.setMaxFilesCount(Long.MAX_VALUE);
        assertFalse(databaseBackupCreateTask.checkTooManyBackups(folder));

    }

    @Test
    @Disabled
    @DisplayName("Проверка переполнения файлов бэкапа по max total files size")
    public void checkTooManyBackupFilesTest() {
        File folder = new File(databaseBackupCreateTask.getLocalPath());

        databaseBackupCreateTask.createBackupAndMoveToGoogleDrive();

        long filesSize = 0;
        for (File file : folder.listFiles()) {
            filesSize += file.length();
        }

        databaseBackupCreateTask.setMaxTotalFilesSize(filesSize - 1);
        assertTrue(databaseBackupCreateTask.checkTooManyBackups(folder));

        databaseBackupCreateTask.setMaxTotalFilesSize(filesSize + 1);
        assertFalse(databaseBackupCreateTask.checkTooManyBackups(folder));

    }

    @Test
    @Disabled
    @DisplayName("Проверка удаления бэкапов")
    public void deleteBackupTest(){
        File folder = new File(databaseBackupCreateTask.getLocalPath());

        databaseBackupCreateTask.createBackupAndMoveToGoogleDrive();

        long filesCount = folder.listFiles().length;

        System.out.println("Deleted: " + databaseBackupCreateTask.cleanOldestBackup(folder));

        File folderAfterDelete = new File(databaseBackupCreateTask.getLocalPath());

        if (filesCount > 0) {
            assertEquals(filesCount, folderAfterDelete.listFiles().length + 1);
        } else {
            assertEquals(filesCount, 0);
            assertEquals(folderAfterDelete.listFiles().length, 0);
        }

    }

    @Test
    @Disabled
    @DisplayName("Проверка превышения количества попыток удаления старых бэкапов")
    public void deleteBackupTryIterTest() {
        File folder = new File(databaseBackupCreateTask.getLocalPath());

        long maxIter = 3;
        databaseBackupCreateTask.setMaxCleaningIteration(maxIter);

        for (int i = 0; i <= maxIter + 1; i++) {
            databaseBackupCreateTask.createBackupAndMoveToGoogleDrive();
        }

        long filesCountBefore = folder.listFiles().length;

        databaseBackupCreateTask.setMaxTotalFilesSize(1);

        databaseBackupCreateTask.createBackupAndMoveToGoogleDrive();

        assertEquals(folder.listFiles().length, filesCountBefore - maxIter);
    }

}
