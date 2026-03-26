package com.example.legal_system.dto;
import java.util.List;
import java.util.Map;

public record AccessStatisticsDTO(
    Map<String, Long> totalAccess,
    String peakHour,
    List<String> mostVisitedPages
) {

}
