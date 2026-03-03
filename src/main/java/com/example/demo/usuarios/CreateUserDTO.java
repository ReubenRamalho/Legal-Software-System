package com.example.demo.usuarios;

public record CreateUserDTO(
    String name,
    String email,
    String type,
    String login,
    String senha
) {
}
