package ru.skillbox.socialnetwork.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LanguageResponse {
    public String error;
    public long timestamp;
    public int total;
    public int offset;
    public int perPage;
    public List<Datum> data;

    @Data
    @Builder
    public static class Datum{
        public int id;
        public String title;
    }
}
