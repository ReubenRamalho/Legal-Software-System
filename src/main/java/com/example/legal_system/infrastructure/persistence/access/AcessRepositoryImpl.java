package com.example.legal_system.infrastructure.persistence.access;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.dto.AccessRecordDTO;
import com.example.legal_system.model.Access;

@Repository
public class AcessRepositoryImpl implements IAccessRepository {

    private final AccessJpaRepository acessoJpaRepository;

    public AcessRepositoryImpl(AccessJpaRepository acessoJpaRepository) {
        this.acessoJpaRepository = acessoJpaRepository;
    }

    @Override
    public List<AccessRecordDTO> findByHourDateAccessBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime inicio = startDate.atStartOfDay();
        LocalDateTime fim = endDate.atTime(LocalTime.MAX);

        List<Access> acessos = acessoJpaRepository.findByHourDateAccessBetween(inicio, fim);

        return acessos.stream()
                .map(acesso -> new AccessRecordDTO(
                        acesso.getUserId(), 
                        acesso.getHourDateAccess(), 
                        acesso.getVisitedPage()))
                .collect(Collectors.toList());
    }
}