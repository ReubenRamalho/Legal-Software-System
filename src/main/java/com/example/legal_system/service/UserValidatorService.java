package com.example.legal_system.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.ILogger;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.enums.UserType;

/**
 * Validates user data before creation and update operations.
 *
 * <p>Centralizes all business rules related to user input, such as login format,
 * email presence, password complexity, and uniqueness constraints. Violations
 * are reported as {@link IllegalArgumentException}s.</p>
 */
@Component
public class UserValidatorService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 128;
    private static final int MIN_REQUIRED_COMPLEXITY_TYPES = 3;
    private static final int MAX_LOGIN_LENGTH = 12;

    private final IUserRepository userRepository;
    private final ILogger logger;

    public UserValidatorService(RepositoryFactory repositoryFactory, ILogger logger) {
        this.userRepository = repositoryFactory.getUserRepository();
        this.logger = logger;
    }

    /**
     * Validates all fields required for creating a new user.
     *
     * @param dto the creation payload.
     * @throws IllegalArgumentException if any validation rule is violated.
     */
    public void validateCreateUser(CreateUserDTO dto) {
        validateLogin(dto.login());
        validateLoginAvailable(dto.login());
        validateEmail(dto.email());
        validateType(dto.type());
        validatePassword(dto.password(), dto.login(), dto.email());
    }

    /**
     * Validates the fields provided in a user update request.
     *
     * <p>Only non-null fields are validated. The existing user's current values
     * are used as context when validating password rules.</p>
     *
     * @param userId the ID of the user being updated.
     * @param dto    the (already normalized) update payload.
     * @throws IllegalArgumentException if any validation rule is violated.
     */
    public void validateUpdateUser(String userId, UpdateUserDTO dto) {
        UpdateUserDTO normalizedDto = dto.normalized();
        String login = normalizedDto.login();
        String email = normalizedDto.email();
        String type = normalizedDto.type();
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

        if (type != null) {
            validateType(type);
        }

        if (password != null) {
            String effectiveLogin = login != null ? login : existingUser.getLogin();
            String effectiveEmail = email != null ? email : existingUser.getEmail();
            validatePassword(password, effectiveLogin, effectiveEmail);
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

    private void validateType(String type) {
        UserType.fromInput(type);
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

    private void validatePassword(String password, String login, String email) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be between 8 and 128 characters long");
        }

        if (password.equals(login)) {
            throw new IllegalArgumentException("Password must not be equal to the login");
        }

        if (password.equals(email)) {
            throw new IllegalArgumentException("Password must not be equal to the email");
        }

        int complexityScore = 0;

        if (Pattern.compile("[A-Z]").matcher(password).find()) {
            complexityScore++;
        }
        if (Pattern.compile("[a-z]").matcher(password).find()) {
            complexityScore++;
        }
        if (Pattern.compile("[0-9]").matcher(password).find()) {
            complexityScore++;
        }
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            complexityScore++;
        }

        if (complexityScore < MIN_REQUIRED_COMPLEXITY_TYPES) {
            throw new IllegalArgumentException(
                    "Password must contain at least 3 of the following character types: " +
                    "uppercase letters, lowercase letters, digits, and special characters");
        }
    }
}
