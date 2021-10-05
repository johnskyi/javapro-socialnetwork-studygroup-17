package ru.skillbox.socialnetwork.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetwork.utils.YamlPropertySourceFactory;

@Component
@PropertySource(value = "classpath:dropbox.yaml", factory = YamlPropertySourceFactory.class)
public class DropBoxConfig {

    Logger logger = LoggerFactory.getLogger(DropBoxConfig.class);

    @Value("${dropbox.token}")
    private String ACCESS_TOKEN;

    @Value("${dropbox.folder}")
    private String folderName;

    public DbxClientV2 getClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(folderName).build();
        logger.info("Get DropBox config:\n\t\t Access token: {}\n\t\t Path: {}", ACCESS_TOKEN, folderName);
        return new DbxClientV2(config, ACCESS_TOKEN);
    }
}
