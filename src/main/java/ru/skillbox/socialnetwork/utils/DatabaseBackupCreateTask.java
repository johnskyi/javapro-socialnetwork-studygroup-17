package ru.skillbox.socialnetwork.utils;

import com.google.api.client.http.FileContent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetwork.service.GoogleDriveService;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Component
@RequiredArgsConstructor
public class DatabaseBackupCreateTask {

    private final GoogleDriveService googleDriveService;

    private static final String VALID_SYSTEM_NAME = "Linux";
    private static final SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private static final FilenameFilter filenameFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            try {
                fileNameDateFormat.parse(name.substring(0, name.indexOf('.')));
            }catch (ParseException e){
                return false;
            }
            return true;
        }
    };

    private static final Logger log = LoggerFactory.getLogger(DatabaseBackupCreateTask.class);

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
    @Scheduled(cron = "0 0 3 * * *")
    // One day
    //@Scheduled(fixedRate = 1000 * 60 * 60 * 12)
    public void createBackupAndMoveToGoogleDrive(){
        if (!System.getProperty("os.name").equals(VALID_SYSTEM_NAME)){
            log.info("Creating database backup failed. This option implement only for linux system");
            return;
        }

        File localFolder = new java.io.File(localPath);
        try {
            FileUtils.forceMkdir(localFolder);
        }catch (IOException e){
            log.info("Creating database backup directory failed: " + e.getMessage());
        }

        long iter = 0;

        while(checkTooManyBackups(localFolder)){
            if((iter++ >= maxCleaningIteration)) {
                log.info("Done " + maxCleaningIteration + " try for clean backups and no effect. Process backup failed");
                return;
            }
            log.info("Backup delete: " + cleanOldestBackup(localFolder));
        };

        String simpleFileName = fileNameDateFormat.format(new Date()) + ".tar";

        String cmd = "pg_dump" +
                " --host=" + host +
                " --username=javapro" +
                " --file=" + localPath + simpleFileName +
                " --format=tar";

        try {
            Runtime.getRuntime().exec(cmd).waitFor();
            log.info("Created database backup file:" + localPath + simpleFileName);
        }catch (IOException e){
            log.info("Created database backup file error:" + e.getMessage());
        }catch (InterruptedException e){
            log.info("Created database backup file error:" + e.getMessage());
        }

        FileContent content = new FileContent("application/x-tar", new java.io.File(localPath + simpleFileName));

        try {
            googleDriveService.uploadFile(content, simpleFileName);
            log.info("Backup file uploaded to google drive");
        }catch (Exception e){
            log.info("Backup file uploading to google drive error: " + e.getMessage());
        }
    }

    public boolean checkTooManyBackups(File folder){
        if(!folder.exists() || !folder.isDirectory() || folder.listFiles(filenameFilter).length == 0){
            return false;
        }

        if(folder.getUsableSpace() < minFreeSpace){
            log.info("Too many backups: enabled minimal FreeSpace = " + minFreeSpace + " but now " + folder.getUsableSpace());
            return true;
        };

        long filesCount = 0;
        long filesSize = 0;

        for (File file : folder.listFiles()){
            filesCount++;
            filesSize += file.length();
        }

        if(filesCount > maxFilesCount){
            log.info("Too many backups: enabled maximal files count = " + maxFilesCount + " but now " + filesCount);
            return true;
        };

        if(filesSize > maxTotalFilesSize){
            log.info("Too many backups: enabled maximal total files size = " + maxTotalFilesSize + " but now " + filesSize);
            return true;
        };

        return false;

    }

    public String cleanOldestBackup(File folder) {

        if (!folder.exists() || !folder.isDirectory() || folder.listFiles(filenameFilter).length == 0) {
            return "error delete";
        }

        File[] files = folder.listFiles(filenameFilter);

        File candidate = files[0];

        for (File file : files) {
            try {
                if (fileNameDateFormat.parse(file.getName().substring(0, file.getName().indexOf('.')))
                        .before(fileNameDateFormat.parse(candidate.getName().substring(0, candidate.getName().indexOf('.'))))) {
                    candidate = file;
                }
            } catch (ParseException parseException) {

            }
        }
        if (candidate.delete()) {
            return candidate.getName();
        } else {
            return "error delete";
        }
    }
}
