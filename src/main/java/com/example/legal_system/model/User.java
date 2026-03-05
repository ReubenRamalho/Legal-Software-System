package com.example.legal_system.model;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
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

    public User(String name, String email, String type, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        this.email = Objects.requireNonNull(email, "Email não pode ser nulo");
        this.type = Objects.requireNonNull(type, "Tipo não pode ser nulo");
        this.login = Objects.requireNonNull(login, "Login não pode ser nulo");
        this.password = Objects.requireNonNull(password, "Senha não pode ser nulo");
    }
}