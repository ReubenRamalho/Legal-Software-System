package com.example.legal_system.service;

import org.springframework.stereotype.Component;
import com.example.legal_system.domain.IAcessoRepository;
import com.example.legal_system.dto.EstatisticasAcessoDTO;

@Component("HTML")
public class RelatorioAcessoHTML extends GeradorRelatorioTemplate {

    public RelatorioAcessoHTML(IAcessoRepository acessoRepository) {
        super(acessoRepository);
    }

    @Override
    protected byte[] formatarSaida(EstatisticasAcessoDTO estatisticas) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>Relatório de Acessos</h1>");
        html.append("<p>Horário de Pico: ").append(estatisticas.horarioDePico()).append("</p>");
        // ... iteração para montar tabelas HTML para acessos por usuário e páginas
        html.append("</body></html>");
        
        return html.toString().getBytes();
    }

    @Override
    protected String salvar(byte[] arquivoFinal) {
        // Lógica para salvar o arquivo em disco ou S3
        String caminho = "/reports/html/relatorio_" + System.currentTimeMillis() + ".html";
        System.out.println("Salvando arquivo HTML em: " + caminho);
        // Files.write(Path.of(caminho), arquivoFinal);
        return caminho;
    }
}