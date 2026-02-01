package com.example.demo.usuarios;

import lombok.Getter;

@Getter
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
}
