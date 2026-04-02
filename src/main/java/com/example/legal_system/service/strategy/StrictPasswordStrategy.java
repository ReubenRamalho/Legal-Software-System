package com.example.legal_system.service.strategy;

import java.util.regex.Pattern;

/**
 * Strict password validation strategy for {@code SOCIO_ADMINISTRADOR} users.
 *
 * <p>Rules enforced:
 * <ul>
 *   <li>Minimum 12 characters, maximum 128 characters.</li>
 *   <li>Must not equal the login or the email.</li>
 *   <li>Must contain <b>all 4</b> character types: uppercase letters, lowercase letters,
 *       digits, and special characters.</li>
 * </ul>
 * </p>
 *
 * <p>This is the most demanding policy, applied to users with the highest privilege
 * level in the system.</p>
 */
public class StrictPasswordStrategy implements PasswordValidationStrategy {

    private static final int MIN_LENGTH = 12;
    private static final int MAX_LENGTH = 128;
    private static final int REQUIRED_COMPLEXITY_TYPES = 4;

    @Override
    public void validate(String password, String login, String email) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                "Password must be between " + MIN_LENGTH + " and " + MAX_LENGTH
                + " characters long (Strict policy for Sócio-Administrador)");
        }

        if (password.equals(login)) {
            throw new IllegalArgumentException("Password must not be equal to the login");
        }

        if (password.equals(email)) {
            throw new IllegalArgumentException("Password must not be equal to the email");
        }

        int score = 0;
        if (Pattern.compile("[A-Z]").matcher(password).find()) score++;
        if (Pattern.compile("[a-z]").matcher(password).find()) score++;
        if (Pattern.compile("[0-9]").matcher(password).find()) score++;
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) score++;

        if (score < REQUIRED_COMPLEXITY_TYPES) {
            throw new IllegalArgumentException(
                "Password must contain all 4 character types: uppercase letters, "
                + "lowercase letters, digits, and special characters (Strict policy)");
        }
    }
}
