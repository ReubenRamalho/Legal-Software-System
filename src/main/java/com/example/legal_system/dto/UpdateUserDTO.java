package com.example.legal_system.dto;

public record UpdateUserDTO(
    String name,
    String email,
    String type,
    String login,
    String password
) {
    public UpdateUserDTO normalized() {
        return new UpdateUserDTO(
                normalize(name),
                normalize(email),
                normalize(type),
                normalize(login),
                normalize(password));
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
