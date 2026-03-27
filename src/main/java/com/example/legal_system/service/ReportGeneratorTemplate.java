package com.example.legal_system.service;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.stream.Collectors;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.dto.AccessStatisticsDTO;
import com.example.legal_system.dto.AccessRecordDTO;

/**
 * Abstract base class that defines the Template Method for report generation.
 *
 * <p>Implements the <b>Template Method</b> pattern: {@link #generateReport(LocalDate, LocalDate)}
 * defines the fixed algorithm skeleton — extract → process → format → save — while
 * the {@link #formatOutput(AccessStatisticsDTO)} and {@link #save(byte[])} steps are
 * left abstract for subclasses to implement according to the desired output format.</p>
 *
 * <p>The data extraction and statistics processing steps are provided as {@code protected}
 * methods so subclasses may override them if needed.</p>
 *
 * @see HtmlAccessReport
 * @see PdfAccessReport
 */
public abstract class ReportGeneratorTemplate {

    private final IAccessRepository accessRepository;

    protected ReportGeneratorTemplate(IAccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    /**
     * Template Method — defines the fixed four-step report generation algorithm.
     *
     * <ol>
     *   <li>Extract raw access data from the repository.</li>
     *   <li>Process the raw data into aggregated statistics.</li>
     *   <li>Format the statistics into the target output format (delegated to subclass).</li>
     *   <li>Save the formatted output and return the file path (delegated to subclass).</li>
     * </ol>
     *
     * @param startDate the beginning of the reporting period.
     * @param endDate   the end of the reporting period.
     * @return the absolute path of the saved report file.
     */
    public final String generateReport(LocalDate startDate, LocalDate endDate) {
        List<AccessRecordDTO> rawData = extractData(startDate, endDate);
        AccessStatisticsDTO statistics = processStatistics(rawData);
        byte[] formattedReport = formatOutput(statistics);
        return save(formattedReport);
    }

    /**
     * Retrieves raw access records for the given date range.
     *
     * <p>This step is implemented in the base class and shared by all subclasses.</p>
     *
     * @param startDate the start of the period.
     * @param endDate   the end of the period.
     * @return a list of raw {@link AccessRecordDTO} objects.
     */
    protected List<AccessRecordDTO> extractData(LocalDate startDate, LocalDate endDate) {
        return accessRepository.findByHourDateAccessBetween(startDate, endDate);
    }

    /**
     * Computes aggregated statistics from the raw access records.
     *
     * <p>Calculates:</p>
     * <ul>
     *   <li>Total access count per user.</li>
     *   <li>Peak access hour (the one-hour window with the most accesses).</li>
     *   <li>Top 3 most visited pages.</li>
     * </ul>
     *
     * @param rawData the raw access records to analyze.
     * @return a populated {@link AccessStatisticsDTO}.
     */
    protected AccessStatisticsDTO processStatistics(List<AccessRecordDTO> rawData) {
        // Total access count grouped by user ID
        Map<String, Long> totalAccess = rawData.stream()
            .collect(Collectors.groupingBy(AccessRecordDTO::userId, Collectors.counting()));

        // Peak hour: groups accesses by hour-of-day (0–23) and picks the maximum
        String peakHour = rawData.stream()
            .collect(Collectors.groupingBy(d -> d.hourDateAccess().getHour(), Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(entry -> entry.getKey() + "h - " + (entry.getKey() + 1) + "h")
            .orElse("No data");

        // Top 3 most visited pages
        List<String> mostVisitedPages = rawData.stream()
            .collect(Collectors.groupingBy(AccessRecordDTO::visitedPage, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(3)
            .map(Map.Entry::getKey)
            .toList();

        return new AccessStatisticsDTO(totalAccess, peakHour, mostVisitedPages);
    }

    /**
     * Formats the aggregated statistics into the target file format (e.g., HTML, PDF bytes).
     *
     * @param statistics the computed statistics to render.
     * @return the formatted report content as a byte array.
     */
    protected abstract byte[] formatOutput(AccessStatisticsDTO statistics);

    /**
     * Persists the formatted report to disk and returns its file path.
     *
     * @param formattedReport the byte array content to write.
     * @return the absolute path of the saved file.
     */
    protected abstract String save(byte[] formattedReport);
}
