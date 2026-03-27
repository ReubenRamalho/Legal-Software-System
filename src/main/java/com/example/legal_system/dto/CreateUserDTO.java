package com.example.legal_system.dto;

/**
 * Payload for creating a new user.
 *
 * @param name     the user's full name.
 * @param email    the user's email address.
 * @param type     the user's role as a raw string (resolved to {@link com.example.legal_system.enums.UserType}).
 * @param login    the desired login identifier (max 12 characters, letters only).
 * @param password the desired password (must meet complexity requirements).
 */
public record CreateUserDTO(
    String name,
    String email,
    String type,
    String login,
    String password
) {
}
