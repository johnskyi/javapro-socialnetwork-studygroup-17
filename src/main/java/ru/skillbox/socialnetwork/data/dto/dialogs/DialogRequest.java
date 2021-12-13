package ru.skillbox.socialnetwork.data.dto.dialogs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DialogRequest {
    private long dialogId;
    private String message;
}
