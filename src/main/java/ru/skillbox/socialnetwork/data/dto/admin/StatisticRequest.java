package ru.skillbox.socialnetwork.data.dto.admin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticRequest {
    String dateFromGraph;
    String dateToGraph;
    String graphPeriod;
    String dateFromDiagram;
    String dateToDiagram;
    String diagramPeriod;
}
