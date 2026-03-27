package com.example.legal_system.dto;

import java.util.List;

/**
 * Payload for creating a new legal process.
 *
 * @param numberCnj   the unique CNJ case number (must be unique across all processes).
 * @param title       a short title describing the case.
 * @param description the full description of the legal case.
 * @param clientName  the full name of the client being represented.
 * @param court       the court where the case is being handled.
 * @param district    the judicial district responsible for the case.
 * @param lawyersIds  an optional list of user IDs for lawyers to associate with this process;
 *                    may be {@code null} or empty.
 */
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
