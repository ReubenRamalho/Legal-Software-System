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

    public PdfAccessReport(IAccessRepository accessRepository) {
        super(accessRepository);
    }

    @Override
    protected byte[] formatOutput(AccessStatisticsDTO statistics) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("=== ACCESS REPORT ==="));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Peak Hour: " + statistics.peakHour()));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("--- Most Visited Pages ---"));
            for (String page : statistics.mostVisitedPages()) {
                document.add(new Paragraph("- " + page));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("--- Access Count by User ---"));
            for (Map.Entry<String, Long> entry : statistics.totalAccess().entrySet()) {
                document.add(new Paragraph("User ID " + entry.getKey() + ": " + entry.getValue()));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to format PDF report: " + e.getMessage(), e);
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