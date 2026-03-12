package com.example.legal_system.infrastructure.persistence.process;

import org.springframework.stereotype.Repository;

import com.example.legal_system.domain.IProcessRepository;
import com.example.legal_system.model.Process;

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
