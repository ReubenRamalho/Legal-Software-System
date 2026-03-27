package com.example.legal_system.dto;

import java.time.LocalDateTime;

/**
 * Read-only projection of a single access log entry, used in report processing.
 *
 * @param userId          the ID of the user who generated the access event.
 * @param hourDateAccess  the exact timestamp of the access.
 * @param visitedPage     the page or resource that was accessed.
 */
public record AccessRecordDTO(
    String userId,
    LocalDateTime hourDateAccess,
    String visitedPage) {
}
