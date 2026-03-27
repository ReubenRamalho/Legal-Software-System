package com.example.legal_system.enums;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Enumeration of user roles recognised by the legal system.
 *
 * <p>Each constant holds a Portuguese display name used for persistence and UI rendering.
 * Input matching is accent- and case-insensitive, allowing flexible user input
 * (e.g., "advogado", "Advogado", and "ADVOGADO" all resolve to {@link #ADVOGADO}).</p>
 */
public enum UserType {
    SOCIO_ADMINISTRADOR("Sócio-Administrador"),
    SOCIO("Sócio"),
    ADVOGADO("Advogado"),
    ESTAGIARIO("Estagiário");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable display name for this user type.
     *
     * @return the display name string.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Resolves a {@link UserType} from a user-provided string.
     *
     * <p>The match is performed after normalizing both the input and display names:
     * accents are stripped, hyphens are treated as spaces, and the comparison is
     * case-insensitive.</p>
     *
     * @param input the raw string provided by the user.
     * @return the matching {@link UserType}.
     * @throws IllegalArgumentException if the input does not match any known type
     *                                  or is blank.
     */
    public static UserType fromInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("User type cannot be blank");
        }

        String normalizedInput = normalize(input);

        return Arrays.stream(values())
                .filter(type -> normalize(type.displayName).equals(normalizedInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid user type. Allowed values: " + allowedValues()));
    }

    /**
     * Returns a comma-separated string of all valid display names.
     *
     * @return human-readable list of allowed user types.
     */
    public static String allowedValues() {
        return Arrays.stream(values())
                .map(UserType::getDisplayName)
                .collect(Collectors.joining(", "));
    }

    /**
     * Normalizes a string for comparison by stripping accents, trimming whitespace,
     * replacing hyphens with spaces, collapsing multiple spaces, and lowercasing.
     *
     * @param value the string to normalize.
     * @return the normalized string.
     */
    private static String normalize(String value) {
        String withoutAccents = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        return withoutAccents
                .trim()
                .replace('-', ' ')
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }
}