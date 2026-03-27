package com.example.legal_system.infrastructure.persistence.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.Process;

/**
 * Spring Data JPA repository for {@link Process} entities.
 *
 * <p>Provides standard CRUD operations inherited from {@link JpaRepository}.
 * This interface is used internally by {@link ProcessRepositoryImpl} and
 * should not be injected directly by the business layer.</p>
 */
@Repository
public interface ProcessJpaRepository extends JpaRepository<Process, String> {
}
