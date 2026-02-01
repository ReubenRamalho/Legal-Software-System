package com.example.demo.usuarios;

public class CreateUserDTO {

    private final String name;
    private final String email;
    private final String type;
    private final String login;
    private final String senha;

    public CreateUserDTO(String name, String email, String type, String login, String senha) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.login = login;
        this.senha = senha;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
