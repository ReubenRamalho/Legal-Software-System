package com.example.legal_system.service.strategy;

/**
 * Strategy interface for password validation policies.
 *
 * <p>Defines the contract for the <b>Strategy</b> pattern applied to user password
 * validation. Different user types require different levels of password complexity:
 * a {@code Sócio-Administrador} handles sensitive data and therefore needs a stricter
 * policy, while an intern ({@code Estagiário}) can operate with a lighter one.</p>
 *
 * <p>Concrete implementations:
 * <ul>
 *   <li>{@link StrictPasswordStrategy}   — for {@code SOCIO_ADMINISTRADOR}: all 4 complexity types, min 12 chars.</li>
 *   <li>{@link StandardPasswordStrategy} — for {@code SOCIO} and {@code ADVOGADO}: 3 of 4 types, min 8 chars.</li>
 *   <li>{@link BasicPasswordStrategy}    — for {@code ESTAGIARIO}: 2 of 4 types, min 6 chars.</li>
 * </ul>
 * </p>
 *
 * <p>The strategy is selected at runtime by
 * {@link com.example.legal_system.service.strategy.PasswordStrategyFactory} based on the
 * user's {@link com.example.legal_system.enums.UserType}.</p>
 */
public interface PasswordValidationStrategy {

    /**
     * Validates the given password against this strategy's rules.
     *
     * @param password the raw password to validate.
     * @param login    the user's login (passwords must not equal the login).
     * @param email    the user's email (passwords must not equal the email).
     * @throws IllegalArgumentException if the password violates any rule of this strategy.
     */
    void validate(String password, String login, String email);
}
