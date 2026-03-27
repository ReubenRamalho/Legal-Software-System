package com.example.legal_system.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.legal_system.domain.ILogger;

/**
 * Service responsible for orchestrating access report generation.
 *
 * <p>Acts as the entry point for report requests. It delegates the actual
 * generation to the appropriate {@link ReportGeneratorTemplate} strategy,
 * selected by the format parameter (e.g., {@code "HTML"}, {@code "PDF"}).</p>
 *
 * <p>Spring automatically populates the {@code generators} map with all
 * {@link ReportGeneratorTemplate} beans, using their {@code @Component} name
 * as the key (e.g., {@code "HTML"} → {@link HtmlAccessReport}).</p>
 */
@Service
public class AccessReportService {

    private final Map<String, ReportGeneratorTemplate> generators;
    private final ILogger logger;

    public AccessReportService(Map<String, ReportGeneratorTemplate> generators, ILogger logger) {
        this.generators = generators;
        this.logger = logger;
    }

    /**
     * Generates an access report for the given date range in the requested format.
     *
     * @param format    the desired output format (case-insensitive, e.g., {@code "HTML"} or {@code "PDF"}).
     * @param startDate the start of the reporting period (inclusive).
     * @param endDate   the end of the reporting period (inclusive).
     * @return the absolute file path where the generated report was saved.
     * @throws IllegalArgumentException if the format is not supported.
     */
    public String requestReport(String format, LocalDate startDate, LocalDate endDate) {
        ReportGeneratorTemplate generator = generators.get(format.toUpperCase());

        if (generator == null) {
            logger.warn("Unsupported report format requested: " + format);
            throw new IllegalArgumentException(
                    "Invalid report format. Supported formats: " + generators.keySet());
        }

        logger.info("Starting report generation. Format: " + format);

        String path = generator.generateReport(startDate, endDate);

        logger.info("Report generated successfully at: " + path);
        return path;
    }
}