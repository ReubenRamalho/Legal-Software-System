package com.example.legal_system.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "acessos")
@Getter
@Setter
public class Acesso {

    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "data_hora_acesso", nullable = false)
    private LocalDateTime dataHoraAcesso;

    @Column(name = "pagina_visitada", nullable = false)
    private String paginaVisitada;

    protected Acesso() {
    }

    private Acesso(String userId, LocalDateTime dataHoraAcesso, String paginaVisitada) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.dataHoraAcesso = dataHoraAcesso;
        this.paginaVisitada = paginaVisitada;
    }

    public static Acesso create(String userId, LocalDateTime dataHoraAcesso, String paginaVisitada) {
        return new Acesso(userId, dataHoraAcesso, paginaVisitada);
    }
}