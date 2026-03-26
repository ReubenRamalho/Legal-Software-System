package com.example.legal_system.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.legal_system.enums.StatusProcess;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "processes")
@Getter
@Setter
public class Process {

    @Id
    private String id;

    @Column(name = "number_cnj", unique = true, nullable = false)
    private String numberCnj;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProcess status;

    private String clientName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String court;
    private String district;

    @ManyToMany
    @JoinTable(name = "processes_users", joinColumns = @JoinColumn(name = "process_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> lawyers = new ArrayList<>();

    protected Process() {
    }

    private Process(String numberCnj, String title, String description, String clientName,
            String court, String district) {
        this.id = UUID.randomUUID().toString();
        this.numberCnj = numberCnj;
        this.title = title;
        this.description = description;
        this.clientName = clientName;
        this.status = StatusProcess.ACTIVE;
        this.court = court;
        this.district = district;
    }

    public static Process create(String numberCnj, String title, String description, String clientName,
            String court, String district) {
        return new Process(numberCnj, title, description, clientName, court, district);
    }

    public void addLawyer(User lawyer) {
        if (lawyer != null && !this.lawyers.contains(lawyer)) {
            this.lawyers.add(lawyer);
        }
    }
}
