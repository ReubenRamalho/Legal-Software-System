package com.example.legal_system.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.User;

/**
 * Spring Data JPA repository for {@link User} entities.
 *
 * <p>Provides CRUD operations inherited from {@link JpaRepository} plus
 * custom query methods derived from Spring Data naming conventions.
 * This interface is used internally by {@link UserRepositoryImpl} and
 * should not be injected directly by the business layer.</p>
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {

    /**
     * Checks whether any user has the given login.
     *
     * @param login the login string to look up.
     * @return {@code true} if a user with this login exists.
     */
    boolean existsByLogin(String login);

    /**
     * Checks whether any user other than the one with the given ID has the specified login.
     *
     * @param login the login string to look up.
     * @param id    the ID of the user to exclude from the check.
     * @return {@code true} if another user with this login exists.
     */
    boolean existsByLoginAndIdNot(String login, String id);
}
