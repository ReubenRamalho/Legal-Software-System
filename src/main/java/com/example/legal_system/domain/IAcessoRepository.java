package com.example.legal_system.domain;

import java.time.LocalDate;
import java.util.List;

import com.example.legal_system.dto.RegistroAcessoDTO;

public interface IAcessoRepository {
    List<RegistroAcessoDTO> buscarAcessosNoPeriodo(LocalDate dataInicio, LocalDate dataFim);
}