package com.example.legal_system.infrastructure.persistence.acesso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.legal_system.domain.IAcessoRepository;
import com.example.legal_system.dto.RegistroAcessoDTO;
import com.example.legal_system.model.Acesso;

@Repository
public class AcessoRepositoryImpl implements IAcessoRepository {

    private final AcessoJpaRepository acessoJpaRepository;

    public AcessoRepositoryImpl(AcessoJpaRepository acessoJpaRepository) {
        this.acessoJpaRepository = acessoJpaRepository;
    }

    @Override
    public List<RegistroAcessoDTO> buscarAcessosNoPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        // Converte as datas para o início do dia inicial e o final do dia final
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);

        List<Acesso> acessos = acessoJpaRepository.findByDataHoraAcessoBetween(inicio, fim);

        // Mapeia a Entidade JPA para o DTO esperado pela camada de negócio
        return acessos.stream()
                .map(acesso -> new RegistroAcessoDTO(
                        acesso.getUserId(), 
                        acesso.getDataHoraAcesso(), 
                        acesso.getPaginaVisitada()))
                .collect(Collectors.toList());
    }
}