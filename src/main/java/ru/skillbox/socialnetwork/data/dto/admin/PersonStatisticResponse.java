package ru.skillbox.socialnetwork.data.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonStatisticResponse {
    private String error;
    private long timestamp;
    private long totalPersonCount;
    private long foundPersonCount;
    private Map<Timestamp, Long> personGraphData;
    private Map<String, Double> ageDistribution;
    private Map<String, Double> sexDistribution;
}
