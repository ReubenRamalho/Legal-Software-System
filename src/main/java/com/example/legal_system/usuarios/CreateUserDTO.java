package com.example.legal_system.usuarios;

public record CreateUserDTO(
    String name,
    String email,
    String type,
    String login,
    String senha
) {
}
