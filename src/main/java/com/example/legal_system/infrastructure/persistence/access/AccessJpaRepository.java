package com.example.legal_system.infrastructure.persistence.access;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.Access;

@Repository
public interface AccessJpaRepository extends JpaRepository<Access, String> {
    
    List<Access> findByHourDateAccessBetween(LocalDateTime startDate, LocalDateTime endDate);
}