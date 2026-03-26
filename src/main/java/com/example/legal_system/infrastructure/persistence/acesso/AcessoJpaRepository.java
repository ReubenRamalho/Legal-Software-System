package com.example.legal_system.infrastructure.persistence.acesso;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.Acesso;

@Repository
public interface AcessoJpaRepository extends JpaRepository<Acesso, String> {
    
    // O Spring Data gera a query automaticamente para buscar entre duas datas
    List<Acesso> findByDataHoraAcessoBetween(LocalDateTime inicio, LocalDateTime fim);
}