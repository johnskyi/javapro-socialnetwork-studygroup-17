package ru.skillbox.socialnetwork.utils;

import com.google.api.client.http.FileContent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetwork.service.GoogleDriveService;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@Component
@RequiredArgsConstructor
public class BackupGoogleDriveTask {

    private final GoogleDriveService googleDriveService;

    private static final String VALID_SYSTEM_NAME = "Linux";
    private static final DateTimeFormatter fileNameDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final FilenameFilter filenameFilter = (dir, name) -> {
        try {
            fileNameDateFormat.parse(name.substring(0, name.indexOf('.')));
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    };

    private static final Logger log = LoggerFactory.getLogger(BackupGoogleDriveTask.class);

    @Value("${backup.localpath}")
    private String localPath;
    @Value("${backup.host}")
    private String host;

    @Value("${backup.files.maxcount}")
    private long maxFilesCount;

    @Value("${backup.files.maxtotalsize}")
    private long maxTotalFilesSize;

    @Value("${backup.files.minfreespace}")
    private long minFreeSpace;

    @Value("${backup.files.maxcleaningiterations}")
    private long maxCleaningIteration;

    //every day on 3:00
    //@Scheduled(cron = "0 0 3 * * *")
    // One hour
    //@Scheduled(fixedRate = 1000 * 60 * 60)
    @Scheduled(fixedRate = 1000)
    public void createBackupAndMoveToGoogleDrive() {
        if (!System.getProperty("os.name").equals(VALID_SYSTEM_NAME)) {
            log.info("Failed copy backup to google drive. This option implement only for linux system");
            return;
        }

        File localFolder = new File(localPath);

        if(!localFolder.exists()){
            log.info("Failed copy backup to google drive. Local backups folder not exist: " + localFolder.getAbsolutePath());
            return;
        }

        String simpleFileName = LocalDateTime.now().format(fileNameDateFormat) + ".tar";

        loadFileToGoogleDrive(simpleFileName, simpleFileName);
    }

    public boolean loadFileToGoogleDrive(String srcFileName, String destFileName) {
        FileContent content = new FileContent("application/x-tar", new File(localPath + srcFileName));

        try {
            googleDriveService.uploadFile(content, destFileName);
            log.info("File " + localPath + srcFileName + " uploaded to google drive");
            return true;
        } catch (Exception e) {
            log.info("File " + localPath + srcFileName + " uploading to google drive error: " + e.getMessage());
            return false;
        }
    }


}
