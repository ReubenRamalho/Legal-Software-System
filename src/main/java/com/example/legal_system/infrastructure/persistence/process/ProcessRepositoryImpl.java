package com.example.legal_system.infrastructure.persistence.process;

import org.springframework.stereotype.Repository;

import com.example.legal_system.domain.IProcessRepository;
import com.example.legal_system.model.Process;

/**
 * JPA-based implementation of {@link IProcessRepository}.
 *
 * <p>Delegates all operations to {@link ProcessJpaRepository}, acting as an
 * anti-corruption layer that keeps the domain interface free from Spring Data
 * JPA-specific types and annotations.</p>
 */
@Repository
public class ProcessRepositoryImpl implements IProcessRepository {

    private final ProcessJpaRepository processJpaRepository;

    public ProcessRepositoryImpl(ProcessJpaRepository processJpaRepository) {
        this.processJpaRepository = processJpaRepository;
    }

    @Override
    public Process save(Process process) {
        return processJpaRepository.save(process);
    }

    @Override
    public long count() {
        return processJpaRepository.count();
    }
}
