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

/**
 * JPA entity representing a legal process managed by the system.
 *
 * <p>A process tracks a legal case from filing to resolution, including its CNJ number,
 * involved lawyers, current status, and timestamps. Process objects are created via
 * the {@link #create(String, String, String, String, String, String)} factory method,
 * which sets the initial status to {@link StatusProcess#ACTIVE}.</p>
 */
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
    @JoinTable(
        name = "processes_users",
        joinColumns = @JoinColumn(name = "process_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
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

    /**
     * Factory method for creating a new legal process.
     *
     * <p>The process is initialized with status {@link StatusProcess#ACTIVE}.
     * Lawyers can be associated after creation via {@link #addLawyer(User)}.</p>
     *
     * @param numberCnj   the unique CNJ case number.
     * @param title       a short descriptive title for the case.
     * @param description the full description of the case.
     * @param clientName  the name of the client represented.
     * @param court       the court handling the case.
     * @param district    the judicial district.
     * @return a new, unpersisted {@link Process} instance with a generated ID.
     */
    public static Process create(String numberCnj, String title, String description, String clientName,
            String court, String district) {
        return new Process(numberCnj, title, description, clientName, court, district);
    }

    /**
     * Associates a lawyer (user) with this process.
     *
     * <p>Duplicate entries and null values are ignored.</p>
     *
     * @param lawyer the lawyer to add.
     */
    public void addLawyer(User lawyer) {
        if (lawyer != null && !this.lawyers.contains(lawyer)) {
            this.lawyers.add(lawyer);
        }
    }
}
