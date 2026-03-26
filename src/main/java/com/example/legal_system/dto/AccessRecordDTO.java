package com.example.legal_system.dto;

import java.time.LocalDateTime;

public record AccessRecordDTO(
    String userId, 
    LocalDateTime hourDateAccess, 
    String visitedPage) {
}
