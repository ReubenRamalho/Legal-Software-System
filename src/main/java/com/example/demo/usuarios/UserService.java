package com.example.demo.usuarios;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(CreateUserDTO dto) {

        validarLogin(dto.login());
        validarEmail(dto.email());
        validarSenha(dto.senha(), dto.login(), dto.email());

        User user = new User(
            dto.name(),
            dto.email(),
            dto.type(),
            dto.login(),
            dto.senha());

        userRepository.save(user);
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public int countUsers() {
        return (int) userRepository.count();
    }

    private void validarLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser vazio");
        }

        if (login.length() > 12) {
            throw new IllegalArgumentException("Login deve ter no máximo 12 caracteres");
        }

        if (login.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Login não pode conter números");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }

    private void validarSenha(String senha, String login, String email) {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        if (senha.length() < 8 || senha.length() > 128) {
            throw new IllegalArgumentException(
                "Senha deve ter entre 8 e 128 caracteres"
            );
        }

        if (senha.equals(login)) {
            throw new IllegalArgumentException(
                "Senha não pode ser igual ao login"
            );
        }

        if (senha.equals(email)) {
            throw new IllegalArgumentException(
                "Senha não pode ser igual ao email"
            );
        }

        int tiposAtendidos = 0;

        if (Pattern.compile("[A-Z]").matcher(senha).find()) tiposAtendidos++;
        if (Pattern.compile("[a-z]").matcher(senha).find()) tiposAtendidos++;
        if (Pattern.compile("[0-9]").matcher(senha).find()) tiposAtendidos++;
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(senha).find()) tiposAtendidos++;

        if (tiposAtendidos < 3) {
            throw new IllegalArgumentException(
                "Senha deve conter ao menos 3 dos seguintes tipos: " +
                "maiúsculas, minúsculas, números e caracteres especiais"
            );
        }
    }
}
