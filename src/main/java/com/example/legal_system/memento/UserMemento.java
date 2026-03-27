package com.example.legal_system.memento;

/**
 * Memento do padrão Memento.
 *
 * Armazena um snapshot imutável do estado de um {@link com.example.legal_system.model.User}
 * em um determinado instante. Nenhuma lógica de negócio reside aqui — este
 * objeto existe apenas para preservar dados que poderão ser restaurados futuramente.
 *
 * A imutabilidade é garantida pela ausência de setters: uma vez criado,
 * o estado capturado não pode ser alterado.
 */
public class UserMemento {

    private final String id;
    private final String name;
    private final String email;
    private final String type;
    private final String login;
    private final String password;

    public UserMemento(String id, String name, String email, String type, String login, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return id;
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

    public String getPassword() {
        return password;
    }
}
