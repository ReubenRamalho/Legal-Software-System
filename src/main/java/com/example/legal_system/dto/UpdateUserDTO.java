package com.example.legal_system.dto;

/**
 * Payload for partially updating an existing user.
 *
 * <p>All fields are optional: a {@code null} or blank value means the corresponding
 * field should remain unchanged. Use {@link #normalized()} to convert blank strings
 * to {@code null} before applying business rules.</p>
 *
 * @param name     the new full name, or {@code null} to keep the current value.
 * @param email    the new email address, or {@code null} to keep the current value.
 * @param type     the new role as a raw string, or {@code null} to keep the current value.
 * @param login    the new login identifier, or {@code null} to keep the current value.
 * @param password the new password, or {@code null} to keep the current value.
 */
public record UpdateUserDTO(
    String name,
    String email,
    String type,
    String login,
    String password
) {
    /**
     * Returns a copy of this DTO where blank strings are replaced with {@code null},
     * making it easier for callers to check which fields were actually provided.
     *
     * @return a normalized {@link UpdateUserDTO}.
     */
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
