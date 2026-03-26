package com.example.legal_system.service;

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
        html.append("<html><body>");
        html.append("<h1>Relatório de Acessos</h1>");
        html.append("<p>Horário de Pico: ").append(statistics.peakHour()).append("</p>");
        // ... iteração para montar tabelas HTML para acessos por usuário e páginas
        html.append("</body></html>");
        
        return html.toString().getBytes();
    }

    @Override
    protected String save(byte[] formattedReport) {
        // Lógica para salvar o arquivo em disco ou S3
        String path = "/reports/html/relatorio_" + System.currentTimeMillis() + ".html";
        System.out.println("Salvando arquivo HTML em: " + path);
        // Files.write(Path.of(caminho), arquivoFinal);
        return path;
    }
}