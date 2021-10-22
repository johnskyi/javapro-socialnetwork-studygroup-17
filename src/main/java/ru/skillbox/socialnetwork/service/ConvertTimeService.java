package ru.skillbox.socialnetwork.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ConvertTimeService {
    public static LocalDateTime getLocalDateTime(long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long getTimestamp(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
