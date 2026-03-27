package com.example.legal_system.dto;

/**
 * Read-only projection of a user, returned by query operations.
 *
 * <p>Exposes only the fields safe for external consumption, omitting
 * sensitive data such as login credentials and email.</p>
 *
 * @param id   the user's unique identifier.
 * @param name the user's full name.
 * @param type the user's role display name (e.g., {@code "Advogado"}).
 */
public record UserDTO(
        String id,
        String name,
        String type) {
}
