package com.example.legal_system.dto;

import java.util.List;
import java.util.Map;

/**
 * Aggregated access statistics computed from raw access records, used to populate reports.
 *
 * @param totalAccess      a map of user IDs to their total number of accesses in the period.
 * @param peakHour         a human-readable string representing the hour-of-day with the most accesses
 *                         (e.g., {@code "14h - 15h"}), or {@code "No data"} if the period is empty.
 * @param mostVisitedPages the top 3 most accessed pages, ordered by visit count descending.
 */
public record AccessStatisticsDTO(
    Map<String, Long> totalAccess,
    String peakHour,
    List<String> mostVisitedPages
) {
}
