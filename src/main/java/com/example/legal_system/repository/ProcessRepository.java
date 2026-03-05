package com.example.legal_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.Process;

@Repository
public interface ProcessRepository extends JpaRepository<Process, String> {
}
