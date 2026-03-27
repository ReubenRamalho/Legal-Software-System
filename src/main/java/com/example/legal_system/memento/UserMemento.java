package com.example.legal_system.memento;

/**
 * Memento of the Memento pattern.
 *
 * <p>Stores an immutable snapshot of a {@link com.example.legal_system.model.User}'s
 * state at a specific point in time. No business logic resides here — this object
 * exists solely to preserve data that may be restored later.</p>
 *
 * <p>Immutability is guaranteed by the absence of setters: once created,
 * the captured state cannot be modified.</p>
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
