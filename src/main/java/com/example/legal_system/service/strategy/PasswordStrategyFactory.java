package com.example.legal_system.service.strategy;

import org.springframework.stereotype.Component;

import com.example.legal_system.enums.UserType;

/**
 * Factory that selects the appropriate {@link PasswordValidationStrategy} for a given
 * {@link UserType} at runtime.
 *
 * <p>This component is part of the <b>Strategy</b> pattern: instead of hard-coding the
 * password policy inside {@link com.example.legal_system.service.UserValidatorService},
 * the factory encapsulates the selection logic and returns the correct strategy object,
 * keeping the validator open for extension (new types / new policies) without modification.</p>
 *
 * <p>Mapping:
 * <ul>
 *   <li>{@link UserType#SOCIO_ADMINISTRADOR} → {@link StrictPasswordStrategy} (all 4 types, min 12 chars)</li>
 *   <li>{@link UserType#SOCIO}               → {@link StandardPasswordStrategy} (3 of 4, min 8 chars)</li>
 *   <li>{@link UserType#ADVOGADO}            → {@link StandardPasswordStrategy} (3 of 4, min 8 chars)</li>
 *   <li>{@link UserType#ESTAGIARIO}          → {@link BasicPasswordStrategy} (2 of 4, min 6 chars)</li>
 * </ul>
 * </p>
 */
@Component
public class PasswordStrategyFactory {

    private final PasswordValidationStrategy strict;
    private final PasswordValidationStrategy standard;
    private final PasswordValidationStrategy basic;

    public PasswordStrategyFactory() {
        this.strict   = new StrictPasswordStrategy();
        this.standard = new StandardPasswordStrategy();
        this.basic    = new BasicPasswordStrategy();
    }

    /**
     * Returns the {@link PasswordValidationStrategy} appropriate for the given user type.
     *
     * @param userType the role of the user whose password is being validated.
     * @return the matching strategy; never {@code null}.
     */
    public PasswordValidationStrategy getStrategy(UserType userType) {
        return switch (userType) {
            case SOCIO_ADMINISTRADOR -> strict;
            case SOCIO, ADVOGADO    -> standard;
            case ESTAGIARIO         -> basic;
        };
    }
}
