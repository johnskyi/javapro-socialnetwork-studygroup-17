package ru.skillbox.socialnetwork.data.dto.dialogs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.skillbox.socialnetwork.data.entity.ReadStatus;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Data
public class DialogResponse {

    private String error;
    private long timestamp;
    private long total;
    private long offset;
    private long perPage;
    private Data data;

    @Builder
    @lombok.Data
    @JsonInclude(NON_NULL)
    public static class Data {

        private long id;
        private LocalDateTime time;
        private Long author;
        private Long recipientId;
        private String messageText;
        private ReadStatus readStatus;
    }

}
