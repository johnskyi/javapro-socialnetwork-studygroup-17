package ru.skillbox.socialnetwork.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlatformResponse {
    public String error;
    public long timestamp;
    public int total;
    public int offset;
    public int perPage;
    public List<Datum> data;

    @Data
    @Builder
    public static class Datum{
        public Long id;
        public String title;
    }
}
