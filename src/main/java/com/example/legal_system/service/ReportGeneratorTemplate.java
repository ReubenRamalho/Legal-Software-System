package com.example.legal_system.service;

import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.util.stream.Collectors;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.dto.AccessStatisticsDTO;
import com.example.legal_system.dto.AccessRecordDTO;

public abstract class ReportGeneratorTemplate {

    private final IAccessRepository acessoRepository;

    protected ReportGeneratorTemplate(IAccessRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    // 1. O TEMPLATE METHOD (O esqueleto do algoritmo)
    public final String generateReport(LocalDate startDate, LocalDate endDate) {
        List<AccessRecordDTO> rawData = extractData(startDate, endDate);
        AccessStatisticsDTO statistics = processStatistics(rawData);
        byte[] formattedReport = formatOutput(statistics);
        return save(formattedReport);
    }

    // 2. Passos comuns implementados na base
    protected List<AccessRecordDTO> extractData(LocalDate startDate, LocalDate endDate) {
        // Busca os dados através da interface de repositório
        return acessoRepository.findByHourDateAccessBetween(startDate, endDate);
    }

    protected AccessStatisticsDTO processStatistics(List<AccessRecordDTO> rawData) {
        // a. Total de acessos por usuário
        Map<String, Long> totalAccess = rawData.stream()
            .collect(Collectors.groupingBy(AccessRecordDTO::userId, Collectors.counting()));

        // b. Horário de pico (Agrupando pela hora do dia 0-23)
        String peakHour = rawData.stream()
            .collect(Collectors.groupingBy(d -> d.hourDateAccess().getHour(), Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(entry -> entry.getKey() + "h - " + (entry.getKey() + 1) + "h")
            .orElse("Sem dados");

        // c. Top 3 Páginas mais visitadas
        List<String> mostVisitedPages = rawData.stream()
            .collect(Collectors.groupingBy(AccessRecordDTO::visitedPage, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(3)
            .map(Map.Entry::getKey)
            .toList();

        return new AccessStatisticsDTO(totalAccess, peakHour, mostVisitedPages);
    }

    // 3. Passos variáveis delegados para as subclasses
    protected abstract byte[] formatOutput(AccessStatisticsDTO statistics);
    protected abstract String save(byte[] formattedReport);
}
