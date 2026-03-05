package com.example.legal_system.dto;

import java.util.List;

public record CreateProcessDTO(
    String numberCnj,
    String title,
    String description,
    String clientName,
    String court,
    String district,
    List<String> lawyersIds
) {
}
