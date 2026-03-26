package com.example.legal_system.domain;

import java.time.LocalDate;
import java.util.List;

import com.example.legal_system.dto.AccessRecordDTO;

public interface IAccessRepository {
    List<AccessRecordDTO> findByHourDateAccessBetween(LocalDate startDate, LocalDate endDate);
}