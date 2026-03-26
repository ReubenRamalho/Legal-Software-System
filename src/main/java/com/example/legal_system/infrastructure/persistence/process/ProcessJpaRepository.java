package com.example.legal_system.infrastructure.persistence.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.Process;

@Repository
public interface ProcessJpaRepository extends JpaRepository<Process, String> {
}
