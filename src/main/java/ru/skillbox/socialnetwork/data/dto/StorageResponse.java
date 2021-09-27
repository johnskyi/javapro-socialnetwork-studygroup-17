package ru.skillbox.socialnetwork.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StorageResponse {
    public String error;
    public long timestamp;
    public Datum data;

    @Data
    @Builder
    public static class Datum{
        public Long id;
        public Long ownerId;
        public String fileName;
        public String relativeFilePath;
        public String rawFileURL;
        public String fileFormat;
        public Long bytes;
        public String fileType;
        public Long createdAt;
    }
}
