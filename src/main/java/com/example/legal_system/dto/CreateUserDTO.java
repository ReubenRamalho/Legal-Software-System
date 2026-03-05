package com.example.legal_system.dto;

public record CreateUserDTO(
    String name,
    String email,
    String type,
    String login,
    String password
) {
}
