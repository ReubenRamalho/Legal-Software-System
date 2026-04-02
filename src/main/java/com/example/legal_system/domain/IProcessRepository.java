package com.example.legal_system.domain;

import java.util.Optional;

import com.example.legal_system.model.Process;

/**
 * Domain repository contract for {@link Process} persistence operations.
 *
 * <p>Implementations are provided by the infrastructure layer (e.g., JPA),
 * keeping the domain free from framework-specific dependencies.</p>
 */
public interface IProcessRepository {

    /**
     * Persists the given legal process, creating or updating as needed.
     *
     * @param process the process to save.
     * @return the saved process instance.
     */
    Process save(Process process);

    /**
     * Returns the total number of legal processes in the system.
     *
     * @return the process count.
     */
    long count();

    /**
     * Finds a legal process by its unique ID.
     *
     * @param id the UUID string of the process.
     * @return an {@link Optional} containing the process if found, or empty otherwise.
     */
    Optional<Process> findById(String id);
}

