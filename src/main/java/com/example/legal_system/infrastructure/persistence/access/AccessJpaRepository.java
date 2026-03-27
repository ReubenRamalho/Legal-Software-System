package com.example.legal_system.infrastructure.persistence.access;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.Access;

/**
 * Spring Data JPA repository for {@link com.example.legal_system.model.Access} entities.
 *
 * <p>Provides CRUD operations inherited from {@link JpaRepository} plus a custom
 * range query for access log retrieval. This interface is used internally by
 * {@link AccessRepositoryImpl} and should not be injected directly by the business layer.</p>
 */
@Repository
public interface AccessJpaRepository extends JpaRepository<Access, String> {

    /**
     * Returns all access records whose {@code hourDateAccess} timestamp falls between
     * {@code startDate} (inclusive) and {@code endDate} (inclusive).
     *
     * @param startDate the lower bound of the datetime range.
     * @param endDate   the upper bound of the datetime range.
     * @return a list of matching {@link Access} records.
     */
    List<Access> findByHourDateAccessBetween(LocalDateTime startDate, LocalDateTime endDate);
}