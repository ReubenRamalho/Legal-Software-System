package com.example.legal_system.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.dto.AccessStatisticsDTO;

@Component("HTML")
public class HtmlAccessReport extends ReportGeneratorTemplate {

    public HtmlAccessReport(IAccessRepository acessoRepository) {
        super(acessoRepository);
    }

    @Override
    protected byte[] formatOutput(AccessStatisticsDTO statistics) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset=\"UTF-8\"></head><body>");
        html.append("<h1>Relatório de Acessos</h1>");
        html.append("<p>Horário de Pico: ").append(statistics.peakHour()).append("</p>");
        
        html.append("<h2>Páginas mais visitadas</h2><ul>");
        for (String page : statistics.mostVisitedPages()) {
            html.append("<li>").append(page).append("</li>");
        }
        html.append("</ul>");

        html.append("<h2>Acessos por usuário</h2><ul>");
        statistics.totalAccess().forEach((userId, total) -> {
            html.append("<li>Usuário ID: ").append(userId).append(" - Total: ").append(total).append("</li>");
        });
        html.append("</ul>");

        html.append("</body></html>");
        
        return html.toString().getBytes();
    }

    @Override
    protected String save(byte[] formattedReport) {
        String path = "reports/html/relatorio_" + System.currentTimeMillis() + ".html";
        try {
            Path filePath = Paths.get(path);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, formattedReport);
            return filePath.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar arquivo HTML: " + e.getMessage(), e);
        }
    }
}