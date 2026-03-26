package com.example.legal_system.dto;

import java.time.LocalDateTime;

public record RegistroAcessoDTO(
    String userId, 
    LocalDateTime dataHoraAcesso, 
    String paginaVisitada) {
}
