package com.example.legal_system.memento;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Caretaker of the Memento pattern.
 *
 * <p>Responsible for storing and providing the last {@link UserMemento} for each user.
 * It does not inspect or modify the contents of Mementos — it only stores them
 * by user ID.</p>
 *
 * <p>Only the most recent update for each user is retained: saving a new Memento
 * replaces the previous one. This implements the requirement of
 * "undo only the last update of the entity".</p>
 */
@Component
public class UserMementoCaretaker {

    private final Map<String, UserMemento> history = new HashMap<>();

    /**
     * Saves the Memento representing the state prior to the last update.
     *
     * @param memento the user snapshot to preserve.
     */
    public void save(UserMemento memento) {
        history.put(memento.getId(), memento);
    }

    /**
     * Retrieves the last saved Memento for the given user.
     *
     * @param userId the user's identifier.
     * @return an {@link Optional} containing the Memento, or empty if no update
     *         has been recorded for this user.
     */
    public Optional<UserMemento> getLast(String userId) {
        return Optional.ofNullable(history.get(userId));
    }

    /**
     * Removes the Memento for the given user after the undo operation is complete,
     * preventing the same undo from being applied twice.
     *
     * @param userId the user's identifier.
     */
    public void clear(String userId) {
        history.remove(userId);
    }
}
