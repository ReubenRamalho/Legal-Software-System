package com.example.legal_system.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.ILogger;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.enums.UserType;

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

    public void validateCreateUser(CreateUserDTO dto) {
        validateLogin(dto.login());
        validateLoginAvailable(dto.login());
        validateEmail(dto.email());
        validateType(dto.type());
        validatePassword(dto.password(), dto.login(), dto.email());
    }

    public void validateUpdateUser(String userId, UpdateUserDTO dto) {
        UpdateUserDTO normalizedDto = dto.normalized();
        String login = normalizedDto.login();
        String email = normalizedDto.email();
        String type = normalizedDto.type();
        String password = normalizedDto.password();
        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("Validação de atualização falhou: usuário não encontrado. ID: " + userId);
                    return new IllegalArgumentException("Usuário não encontrado");
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
            logger.warn("Validação de criação falhou: login já está em uso. Login: " + login);
            throw new IllegalArgumentException("Login já está em uso");
        }
    }

    private void validateLoginAvailableForUpdate(String userId, String login) {
        if (userRepository.existsByLoginAndIdNot(login, userId)) {
            logger.warn("Validação de atualização falhou: login já está em uso. ID: " + userId + ", login: " + login);
            throw new IllegalArgumentException("Login já está em uso");
        }
    }

    private void validateType(String type) {
        UserType.fromInput(type);
    }

    private void validateLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser vazio");
        }

        if (login.length() > MAX_LOGIN_LENGTH) {
            throw new IllegalArgumentException("Login deve ter no máximo 12 caracteres");
        }

        if (login.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Login não pode conter números");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }

    private void validatePassword(String password, String login, String email) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Senha deve ter entre 8 e 128 caracteres");
        }

        if (password.equals(login)) {
            throw new IllegalArgumentException("Senha não pode ser igual ao login");
        }

        if (password.equals(email)) {
            throw new IllegalArgumentException("Senha não pode ser igual ao email");
        }

        int tiposAtendidos = 0;

        if (Pattern.compile("[A-Z]").matcher(password).find()) {
            tiposAtendidos++;
        }
        if (Pattern.compile("[a-z]").matcher(password).find()) {
            tiposAtendidos++;
        }
        if (Pattern.compile("[0-9]").matcher(password).find()) {
            tiposAtendidos++;
        }
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            tiposAtendidos++;
        }

        if (tiposAtendidos < MIN_REQUIRED_COMPLEXITY_TYPES) {
            throw new IllegalArgumentException(
                    "Senha deve conter ao menos 3 dos seguintes tipos: " +
                            "maiúsculas, minúsculas, números e caracteres especiais");
        }
    }
}
