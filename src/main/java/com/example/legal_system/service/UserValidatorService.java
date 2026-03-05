package com.example.legal_system.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.legal_system.dto.CreateUserDTO;

@Component
public class UserValidatorService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 128;
    private static final int MIN_REQUIRED_COMPLEXITY_TYPES = 3;
    private static final int MAX_LOGIN_LENGTH = 12;

    public void validateCreateUser(CreateUserDTO dto) {
        validateLogin(dto.login());
        validateEmail(dto.email());
        validatePassword(dto.password(), dto.login(), dto.email());
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
