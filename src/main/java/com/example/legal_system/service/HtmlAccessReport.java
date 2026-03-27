package com.example.legal_system.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.dto.AccessStatisticsDTO;

/**
 * HTML implementation of {@link ReportGeneratorTemplate}.
 *
 * <p>Formats the access statistics as a UTF-8 encoded HTML document and writes
 * the file to the {@code reports/html/} directory under the working directory.</p>
 */
@Component("HTML")
public class HtmlAccessReport extends ReportGeneratorTemplate {

    public HtmlAccessReport(IAccessRepository accessRepository) {
        super(accessRepository);
    }

    @Override
    protected byte[] formatOutput(AccessStatisticsDTO statistics) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset=\"UTF-8\"></head><body>");
        html.append("<h1>Access Report</h1>");
        html.append("<p>Peak Hour: ").append(statistics.peakHour()).append("</p>");

        html.append("<h2>Most Visited Pages</h2><ul>");
        for (String page : statistics.mostVisitedPages()) {
            html.append("<li>").append(page).append("</li>");
        }
        html.append("</ul>");

        html.append("<h2>Access Count by User</h2><ul>");
        statistics.totalAccess().forEach((userId, total) -> {
            html.append("<li>User ID: ").append(userId).append(" - Total: ").append(total).append("</li>");
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
            throw new RuntimeException("Failed to save HTML report: " + e.getMessage(), e);
        }
    }
}