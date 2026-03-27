package com.example.legal_system.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA entity representing a system access log entry.
 *
 * <p>Each record captures who accessed the system, when they did so,
 * and which page they visited. Access objects are created by the
 * {@link #create(String, LocalDateTime, String)} factory method.</p>
 */
@Entity
@Table(name = "access")
@Getter
@Setter
public class Access {

    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "hour_date_access", nullable = false)
    private LocalDateTime hourDateAccess;

    @Column(name = "visited_page", nullable = false)
    private String visitedPage;

    protected Access() {
    }

    private Access(String userId, LocalDateTime hourDateAccess, String visitedPage) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.hourDateAccess = hourDateAccess;
        this.visitedPage = visitedPage;
    }

    /**
     * Factory method for creating a new access log entry.
     *
     * @param userId          the ID of the user who accessed the system.
     * @param hourDateAccess  the exact date and time of the access.
     * @param visitedPage     the page or resource that was accessed.
     * @return a new, unpersisted {@link Access} instance with a generated ID.
     */
    public static Access create(String userId, LocalDateTime hourDateAccess, String visitedPage) {
        return new Access(userId, hourDateAccess, visitedPage);
    }
}