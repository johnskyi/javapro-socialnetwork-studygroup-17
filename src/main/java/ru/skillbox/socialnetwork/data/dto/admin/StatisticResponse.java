package ru.skillbox.socialnetwork.data.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticResponse {
    private String error;
    private long timestamp;
    private long totalDataCount;
    private long foundDataCount;
    private Map<Timestamp, Long> graphData;
    private Map<Integer, Double> dataByHour;
}
