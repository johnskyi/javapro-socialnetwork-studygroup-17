package ru.skillbox.socialnetwork.data.dto.Notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.Notification;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private String error;
    private Long timestamp;
    private Long total;
    private Long offset;
    private Long perPage;
    private List<Data> data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data{
        private Long id;
        @JsonProperty("type_id")
        private Long typeId;
        @JsonProperty("sent_time")
        private Long sentTime;
        @JsonProperty("entity_id")
        private Long entityId;
        private String info;

        public Data(Notification notification){
            this.id = notification.getId();
            this.typeId = notification.getType().getId();
            this.sentTime = notification.getTime().toEpochSecond(ZoneOffset.UTC);
            this.entityId = notification.getEntityId();
            this.info = "String";
        }
    }
}
