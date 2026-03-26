package com.example.legal_system.service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.dto.AccessStatisticsDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component("PDF")
public class PdfAccessReport extends ReportGeneratorTemplate {

    public PdfAccessReport(IAccessRepository acessoRepository) {
        super(acessoRepository);
    }

    @Override
    protected byte[] formatOutput(AccessStatisticsDTO estatisticas) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            
            document.add(new Paragraph("=== RELATORIO DE ACESSOS ==="));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Horario de Pico: " + estatisticas.peakHour()));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("--- Paginas Mais Visitadas ---"));
            for (String pagina : estatisticas.mostVisitedPages()) {
                document.add(new Paragraph("- " + pagina));
            }
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("--- Acessos por Usuario ---"));
            for (Map.Entry<String, Long> entry : estatisticas.totalAccess().entrySet()) {
                document.add(new Paragraph("Usuario ID " + entry.getKey() + ": " + entry.getValue()));
            }
            
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao formatar PDF: " + e.getMessage(), e);
        }
    }

    @Override
    protected String save(byte[] formattedReport) {
        String path = "reports/pdf/relatorio_" + System.currentTimeMillis() + ".pdf";
        try {
            Path filePath = Paths.get(path);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, formattedReport);
            return filePath.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar arquivo PDF: " + e.getMessage(), e);
        }
    }
}