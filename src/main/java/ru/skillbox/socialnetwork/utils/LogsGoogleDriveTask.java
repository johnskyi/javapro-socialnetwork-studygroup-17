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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@Component
@RequiredArgsConstructor
public class LogsGoogleDriveTask {

    private final GoogleDriveService googleDriveService;

    private static final String VALID_SYSTEM_NAME = "Linux";
    private static final DateTimeFormatter fileNameDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final FilenameFilter filenameFilter = (dir, name) -> {
        try {
            fileNameDateFormat.parse(name.substring(0, name.indexOf('.')));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    };

    private static final Logger log = LoggerFactory.getLogger(LogsGoogleDriveTask.class);

    @Value("${logs.localpath}")
    private String localPath;

    private static final String loginfoFolderId = "1FMu_ALeaH6KfaaIruLspd-bEzwkF5eQg";
    private static final String logerrorFolderId = "1NO6U88qx2w6qoh2xh_Jyh2uEGShP58lO";

    //every day on 4:00
    @Scheduled(cron = "0 0 4 * * *")

    // One hour
    //@Scheduled(fixedRate = 1000 * 60 * 60)

    public void copyLogsToGoogleDrive() {
        if (!System.getProperty("os.name").equals(VALID_SYSTEM_NAME)) {
            log.info("Failed copy backup to google drive. This option implement only for linux system");
            return;
        }
        loadFilesFromFolder(new File(getLocalPath() + "/info"), loginfoFolderId);
        loadFilesFromFolder(new File(getLocalPath() + "/error"), logerrorFolderId);

    }

    public void loadFilesFromFolder(File folder, String remoteFolderId) {

        String maskForFileCandidateToCopy = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files == null) {
                log.info("No files in backup folder.");
            } else {
                for (File file : files) {
                    if (file.getName().contains(maskForFileCandidateToCopy)) {
                        log.info("Load logs to drive " + file.getName() + " success: " +
                                loadFileToGoogleDrive(file.getAbsolutePath(), file.getName(), remoteFolderId));
                    }
                }
            }
        } else {
            log.error("Folder " + folder.getAbsolutePath() + " is not exists");
        }
    }


    public boolean loadFileToGoogleDrive(String srcFileName, String destFileName, String parentsId) {
        FileContent content = new FileContent("application/x-tar", new File(srcFileName));

        try {
            googleDriveService.uploadFile(content, destFileName, parentsId);
            log.info("File " + srcFileName + " uploaded to google drive");
            return true;
        } catch (Exception e) {
            log.error("File " + srcFileName + " uploadFile to google drive error: " + e.getMessage());
            return false;
        }
    }
}
