package com.example.legal_system.domain;

import java.time.LocalDate;
import java.util.List;

import com.example.legal_system.dto.AccessRecordDTO;

/**
 * Domain repository contract for {@link com.example.legal_system.model.Access} query operations.
 *
 * <p>Implementations are provided by the infrastructure layer (e.g., JPA),
 * keeping the domain free from framework-specific dependencies.</p>
 */
public interface IAccessRepository {

    /**
     * Returns all access records whose timestamp falls within the given date range (inclusive).
     *
     * @param startDate the beginning of the period (start of day).
     * @param endDate   the end of the period (end of day).
     * @return a list of matching {@link AccessRecordDTO} objects, never {@code null}.
     */
    List<AccessRecordDTO> findByHourDateAccessBetween(LocalDate startDate, LocalDate endDate);
}