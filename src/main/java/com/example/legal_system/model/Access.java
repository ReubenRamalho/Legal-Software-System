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
@Table(name = "acess")
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

    public static Access create(String userId, LocalDateTime hourDateAccess, String visitedPage) {
        return new Access(userId, hourDateAccess, visitedPage);
    }
}