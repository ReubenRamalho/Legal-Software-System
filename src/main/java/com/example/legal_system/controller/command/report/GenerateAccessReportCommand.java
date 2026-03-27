package com.example.legal_system.controller.command.report;

import java.time.LocalDate;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.service.AccessReportService;

/**
 * Command responsible for encapsulating the access report generation operation.
 */
public class GenerateAccessReportCommand implements Command<String> {

    private final AccessReportService accessReportService;
    private final String format;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public GenerateAccessReportCommand(
            AccessReportService accessReportService,
            String format,
            LocalDate startDate,
            LocalDate endDate) {
        this.accessReportService = accessReportService;
        this.format = format;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String execute() {
        return accessReportService.requestReport(format, startDate, endDate);
    }
}
