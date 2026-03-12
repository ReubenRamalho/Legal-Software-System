package com.example.legal_system.dto;

public record UpdateUserDTO(
    String name,
    String email,
    String type,
    String login,
    String password
) {
}
