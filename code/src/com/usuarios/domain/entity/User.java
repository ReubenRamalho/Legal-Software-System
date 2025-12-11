package com.usuarios.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final String type;

    public User(String name, String email, String type) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        this.email = Objects.requireNonNull(email, "Email não pode ser nulo");
        this.type = Objects.requireNonNull(type, "Tipo não pode ser nulo");
    }

    public User(String id, String name, String email, String type) {
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        this.email = Objects.requireNonNull(email, "Email não pode ser nulo");
        this.type = Objects.requireNonNull(type, "Tipo não pode ser nulo");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
