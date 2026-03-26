package com.example.legal_system.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.legal_system.domain.ILogger;

@Service
public class AccessReportService {

    // O Spring injeta automaticamente as subclasses: {"HTML": RelatorioAcessoHTML, "PDF": RelatorioAcessoPDF}
    private final Map<String, ReportGeneratorTemplate> generators;
    private final ILogger logger;

    public AccessReportService(Map<String, ReportGeneratorTemplate> generators, ILogger logger) {
        this.generators = generators;
        this.logger = logger;
    }

    public String requestReport(String format, LocalDate startDate, LocalDate endDate) {
        // Pega a estratégia correta (HTML, PDF, etc) baseado no parâmetro
        ReportGeneratorTemplate generator = generators.get(format.toUpperCase());

        if (generator == null) {
            logger.warn("Tentativa de gerar relatório em formato não suportado: " + format);
            throw new IllegalArgumentException("Formato de relatório inválido. Formatos suportados: " + generators.keySet());
        }

        logger.info("Iniciando geração de relatório. Formato: " + format);
        
        // Aciona o Template Method
        String path = generator.generateReport(startDate, endDate);
        
        logger.info("Relatório gerado com sucesso em: " + path);
        return path;
    }
}