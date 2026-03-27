package com.example.legal_system.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.example.legal_system.memento.UserMemento;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String type;
    private String login;
    private String password;

    protected User() {
    }

    private User(String name, String email, String type, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public static User create(String name, String email, String type, String login, String password) {
        return new User(name, email, type, login, password);
    }

    /**
     * Originator — cria um Memento com o estado atual deste usuário.
     *
     * Deve ser chamado imediatamente antes de qualquer atualização, de modo
     * que o snapshot reflita os dados anteriores à modificação.
     *
     * @return um {@link UserMemento} imutável representando o estado atual.
     */
    public UserMemento createMemento() {
        return new UserMemento(id, name, email, type, login, password);
    }

    /**
     * Originator — restaura o estado deste usuário a partir de um Memento.
     *
     * Sobrescreve os campos atuais com os valores preservados no snapshot,
     * efetivando o "desfazer" da última atualização.
     *
     * @param memento o snapshot a ser restaurado.
     */
    public void restoreFromMemento(UserMemento memento) {
        this.name     = memento.getName();
        this.email    = memento.getEmail();
        this.type     = memento.getType();
        this.login    = memento.getLogin();
        this.password = memento.getPassword();
    }
}