package com.example.legal_system.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.legal_system.domain.ILogger;

@Service
public class RelatorioAcessoService {

    // O Spring injeta automaticamente as subclasses: {"HTML": RelatorioAcessoHTML, "PDF": RelatorioAcessoPDF}
    private final Map<String, GeradorRelatorioTemplate> geradores;
    private final ILogger logger;

    public RelatorioAcessoService(Map<String, GeradorRelatorioTemplate> geradores, ILogger logger) {
        this.geradores = geradores;
        this.logger = logger;
    }

    public String solicitarRelatorio(String formato, LocalDate inicio, LocalDate fim) {
        // Pega a estratégia correta (HTML, PDF, etc) baseado no parâmetro
        GeradorRelatorioTemplate gerador = geradores.get(formato.toUpperCase());

        if (gerador == null) {
            logger.warn("Tentativa de gerar relatório em formato não suportado: " + formato);
            throw new IllegalArgumentException("Formato de relatório inválido. Formatos suportados: " + geradores.keySet());
        }

        logger.info("Iniciando geração de relatório. Formato: " + formato);
        
        // Aciona o Template Method
        String caminhoSalvo = gerador.gerarRelatorio(inicio, fim);
        
        logger.info("Relatório gerado com sucesso em: " + caminhoSalvo);
        return caminhoSalvo;
    }
}