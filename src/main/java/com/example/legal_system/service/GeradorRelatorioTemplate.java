package com.example.legal_system.service;

import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.util.stream.Collectors;

import com.example.legal_system.domain.IAcessoRepository;
import com.example.legal_system.dto.EstatisticasAcessoDTO;
import com.example.legal_system.dto.RegistroAcessoDTO;

public abstract class GeradorRelatorioTemplate {

    private final IAcessoRepository acessoRepository;

    protected GeradorRelatorioTemplate(IAcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    // 1. O TEMPLATE METHOD (O esqueleto do algoritmo)
    public final String gerarRelatorio(LocalDate dataInicio, LocalDate dataFim) {
        List<RegistroAcessoDTO> dadosBrutos = extrairDados(dataInicio, dataFim);
        EstatisticasAcessoDTO estatisticas = processarEstatisticas(dadosBrutos);
        byte[] relatorioFormatado = formatarSaida(estatisticas);
        return salvar(relatorioFormatado);
    }

    // 2. Passos comuns implementados na base
    protected List<RegistroAcessoDTO> extrairDados(LocalDate dataInicio, LocalDate dataFim) {
        // Busca os dados através da interface de repositório
        return acessoRepository.buscarAcessosNoPeriodo(dataInicio, dataFim);
    }

    protected EstatisticasAcessoDTO processarEstatisticas(List<RegistroAcessoDTO> dados) {
        // a. Total de acessos por usuário
        Map<String, Long> acessosPorUser = dados.stream()
            .collect(Collectors.groupingBy(RegistroAcessoDTO::userId, Collectors.counting()));

        // b. Horário de pico (Agrupando pela hora do dia 0-23)
        String horarioPico = dados.stream()
            .collect(Collectors.groupingBy(d -> d.dataHoraAcesso().getHour(), Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(entry -> entry.getKey() + "h - " + (entry.getKey() + 1) + "h")
            .orElse("Sem dados");

        // c. Top 3 Páginas mais visitadas
        List<String> paginasMaisVisitadas = dados.stream()
            .collect(Collectors.groupingBy(RegistroAcessoDTO::paginaVisitada, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(3)
            .map(Map.Entry::getKey)
            .toList();

        return new EstatisticasAcessoDTO(acessosPorUser, horarioPico, paginasMaisVisitadas);
    }

    // 3. Passos variáveis delegados para as subclasses
    protected abstract byte[] formatarSaida(EstatisticasAcessoDTO estatisticas);
    protected abstract String salvar(byte[] arquivoFinal);
}
