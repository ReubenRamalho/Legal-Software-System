package com.example.legal_system.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String type;
    private String login;
    private String password;

    protected User() {
    }

    private User(String name, String email, String type, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public static User create(String name, String email, String type, String login, String password) {
        return new User(name, email, type, login, password);
    }
}