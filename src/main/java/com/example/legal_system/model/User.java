package com.example.legal_system.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.example.legal_system.memento.UserMemento;

/**
 * JPA entity representing a system user.
 *
 * <p>Acts as the <b>Originator</b> in the Memento pattern: it can capture its own
 * state via {@link #createMemento()} and restore a previous state via
 * {@link #restoreFromMemento(UserMemento)}. User objects are created through the
 * {@link #create(String, String, String, String, String)} factory method.</p>
 */
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

    /**
     * Factory method for creating a new user.
     *
     * @param name     the user's full name.
     * @param email    the user's email address.
     * @param type     the user's role display name.
     * @param login    the user's login identifier.
     * @param password the user's password.
     * @return a new, unpersisted {@link User} instance with a generated ID.
     */
    public static User create(String name, String email, String type, String login, String password) {
        return new User(name, email, type, login, password);
    }

    /**
     * Originator — creates a Memento capturing the current state of this user.
     *
     * <p>Must be called immediately before any update so that the snapshot reflects
     * the data prior to the modification.</p>
     *
     * @return an immutable {@link UserMemento} representing the current state.
     */
    public UserMemento createMemento() {
        return new UserMemento(id, name, email, type, login, password);
    }

    /**
     * Originator — restores this user's state from the given Memento.
     *
     * <p>Overwrites the current field values with those preserved in the snapshot,
     * effectively performing the "undo" of the last update.
     * Note: the {@code id} field is intentionally not restored, as it never changes.</p>
     *
     * @param memento the snapshot to restore from.
     */
    public void restoreFromMemento(UserMemento memento) {
        this.name     = memento.getName();
        this.email    = memento.getEmail();
        this.type     = memento.getType();
        this.login    = memento.getLogin();
        this.password = memento.getPassword();
    }
}