package ru.skillbox.socialnetwork.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:dropbox.yaml")
public class DropBoxConfig {

    @Value("${dropbox:token}")
    private String ACCESS_TOKEN;

    @Value("${dropbox:folder}")
    private String folderName;

    public DbxClientV2 getClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(folderName).build();
        return new DbxClientV2(config, ACCESS_TOKEN);
    }
}
