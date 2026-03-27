package com.example.legal_system.domain;

import java.util.List;
import java.util.Optional;

import com.example.legal_system.model.User;

/**
 * Domain repository contract for {@link User} persistence operations.
 *
 * <p>Implementations are provided by the infrastructure layer (e.g., JPA),
 * keeping the domain free from framework-specific dependencies.</p>
 */
public interface IUserRepository {

    /**
     * Persists the given user, creating or updating as needed.
     *
     * @param user the user to save.
     * @return the saved user instance.
     */
    User save(User user);

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the user's UUID string.
     * @return an {@link Optional} containing the user, or empty if not found.
     */
    Optional<User> findById(String id);

    /**
     * Returns all users stored in the system.
     *
     * @return a list of all users, never {@code null}.
     */
    List<User> findAll();

    /**
     * Removes the user with the given identifier.
     *
     * @param id the UUID string of the user to delete.
     */
    void deleteById(String id);

    /**
     * Returns the total number of users in the system.
     *
     * @return the user count.
     */
    long count();

    /**
     * Checks whether a user with the given login already exists.
     *
     * @param login the login to check.
     * @return {@code true} if the login is taken, {@code false} otherwise.
     */
    boolean existsByLogin(String login);

    /**
     * Checks whether any user other than the one with the given ID uses the specified login.
     * Used to validate login uniqueness during updates.
     *
     * @param login the login to check.
     * @param id    the ID of the user being updated (excluded from the check).
     * @return {@code true} if another user already has this login, {@code false} otherwise.
     */
    boolean existsByLoginAndIdNot(String login, String id);
}
