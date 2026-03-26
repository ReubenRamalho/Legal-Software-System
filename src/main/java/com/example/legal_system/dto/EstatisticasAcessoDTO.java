package com.example.legal_system.dto;
import java.util.List;
import java.util.Map;

public record EstatisticasAcessoDTO(
    Map<String, Long> totalAcessosPorUsuario,
    String horarioDePico,
    List<String> paginasMaisVisitadas
) {

}
