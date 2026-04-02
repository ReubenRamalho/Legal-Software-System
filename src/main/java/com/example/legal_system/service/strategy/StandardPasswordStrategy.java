package com.example.legal_system.service.strategy;

import java.util.regex.Pattern;

/**
 * Standard password validation strategy for {@code SOCIO} and {@code ADVOGADO} users.
 *
 * <p>Rules enforced:
 * <ul>
 *   <li>Minimum 8 characters, maximum 128 characters.</li>
 *   <li>Must not equal the login or the email.</li>
 *   <li>Must contain at least <b>3 of 4</b> character types: uppercase letters,
 *       lowercase letters, digits, and special characters.</li>
 * </ul>
 * </p>
 *
 * <p>This is the default policy, aligned with the original validation logic
 * that existed before the Strategy pattern was introduced.</p>
 */
public class StandardPasswordStrategy implements PasswordValidationStrategy {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 128;
    private static final int REQUIRED_COMPLEXITY_TYPES = 3;

    @Override
    public void validate(String password, String login, String email) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                "Password must be between " + MIN_LENGTH + " and " + MAX_LENGTH
                + " characters long");
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
                "Password must contain at least 3 of the following character types: "
                + "uppercase letters, lowercase letters, digits, and special characters");
        }
    }
}
