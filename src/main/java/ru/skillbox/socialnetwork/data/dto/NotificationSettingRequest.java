package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationSettingRequest {
    @JsonProperty("notification_type")
    private String notificationType;
    private boolean enable;
}
