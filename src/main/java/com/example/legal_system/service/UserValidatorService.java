package com.example.legal_system.service;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.ILogger;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.enums.UserType;
import com.example.legal_system.service.strategy.PasswordStrategyFactory;
import com.example.legal_system.service.strategy.PasswordValidationStrategy;

/**
 * Validates user data before creation and update operations.
 *
 * <p>Centralizes all business rules related to user input, such as login format,
 * email presence, password complexity, and uniqueness constraints. Violations
 * are reported as {@link IllegalArgumentException}s.</p>
 *
 * <p>Password complexity rules are delegated to a {@link PasswordValidationStrategy}
 * selected at runtime by {@link PasswordStrategyFactory} based on the user's
 * {@link UserType}. This allows different roles to enforce different security
 * policies without changing this class.</p>
 */
@Component
public class UserValidatorService {

    private static final int MAX_LOGIN_LENGTH = 12;

    private final IUserRepository userRepository;
    private final ILogger logger;
    private final PasswordStrategyFactory passwordStrategyFactory;

    public UserValidatorService(RepositoryFactory repositoryFactory, ILogger logger,
            PasswordStrategyFactory passwordStrategyFactory) {
        this.userRepository = repositoryFactory.getUserRepository();
        this.logger = logger;
        this.passwordStrategyFactory = passwordStrategyFactory;
    }

    /**
     * Validates all fields required for creating a new user.
     *
     * <p>The password is validated using the strategy that corresponds to the
     * requested user type.</p>
     *
     * @param dto the creation payload.
     * @throws IllegalArgumentException if any validation rule is violated.
     */
    public void validateCreateUser(CreateUserDTO dto) {
        validateLogin(dto.login());
        validateLoginAvailable(dto.login());
        validateEmail(dto.email());
        UserType userType = validateType(dto.type());
        validatePassword(dto.password(), dto.login(), dto.email(), userType);
    }

    /**
     * Validates the fields provided in a user update request.
     *
     * <p>Only non-null fields are validated. The effective user type for password
     * strategy selection is the one provided in the update DTO, or — if absent —
     * the user's current type stored in the repository.</p>
     *
     * @param userId the ID of the user being updated.
     * @param dto    the (already normalized) update payload.
     * @throws IllegalArgumentException if any validation rule is violated.
     */
    public void validateUpdateUser(String userId, UpdateUserDTO dto) {
        UpdateUserDTO normalizedDto = dto.normalized();
        String login    = normalizedDto.login();
        String email    = normalizedDto.email();
        String type     = normalizedDto.type();
        String password = normalizedDto.password();

        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("Update validation failed: user not found. ID: " + userId);
                    return new IllegalArgumentException("User not found");
                });

        if (login != null) {
            validateLogin(login);
            validateLoginAvailableForUpdate(userId, login);
        }

        if (email != null) {
            validateEmail(email);
        }

        UserType effectiveType;
        if (type != null) {
            effectiveType = validateType(type);
        } else {
            effectiveType = UserType.fromInput(existingUser.getType());
        }

        if (password != null) {
            String effectiveLogin = login != null ? login : existingUser.getLogin();
            String effectiveEmail = email != null ? email : existingUser.getEmail();
            validatePassword(password, effectiveLogin, effectiveEmail, effectiveType);
        }
    }

    private void validateLoginAvailable(String login) {
        if (userRepository.existsByLogin(login)) {
            logger.warn("Create validation failed: login already in use. Login: " + login);
            throw new IllegalArgumentException("Login is already in use");
        }
    }

    private void validateLoginAvailableForUpdate(String userId, String login) {
        if (userRepository.existsByLoginAndIdNot(login, userId)) {
            logger.warn("Update validation failed: login already in use. ID: " + userId + ", login: " + login);
            throw new IllegalArgumentException("Login is already in use");
        }
    }

    /**
     * Validates the type string and returns the resolved {@link UserType}.
     *
     * @param type the raw type string from the request.
     * @return the resolved {@link UserType}.
     * @throws IllegalArgumentException if the type is blank or unrecognised.
     */
    private UserType validateType(String type) {
        return UserType.fromInput(type);
    }

    private void validateLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be blank");
        }

        if (login.length() > MAX_LOGIN_LENGTH) {
            throw new IllegalArgumentException("Login must be at most 12 characters long");
        }

        if (login.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Login must not contain digits");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
    }

    /**
     * Delegates password validation to the strategy selected by the
     * {@link PasswordStrategyFactory} for the given user type.
     *
     * @param password the raw password to validate.
     * @param login    the effective login (used for equality check).
     * @param email    the effective email (used for equality check).
     * @param userType the role of the user, used to select the strategy.
     * @throws IllegalArgumentException if the password violates the selected strategy's rules.
     */
    private void validatePassword(String password, String login, String email, UserType userType) {
        PasswordValidationStrategy strategy = passwordStrategyFactory.getStrategy(userType);
        strategy.validate(password, login, email);
    }
}
