package com.example.legal_system.domain;

/**
 * Abstract factory for obtaining domain repository instances.
 *
 * <p>Follows the Abstract Factory pattern: the business layer depends only on this
 * interface, while the concrete implementation (e.g., {@code JpaRepositoryFactory})
 * lives in the infrastructure layer. This keeps the domain isolated from persistence
 * framework details.</p>
 */
public interface RepositoryFactory {

    /**
     * Returns the repository responsible for {@link com.example.legal_system.model.User} operations.
     *
     * @return a non-null {@link IUserRepository} instance.
     */
    IUserRepository getUserRepository();

    /**
     * Returns the repository responsible for {@link com.example.legal_system.model.Process} operations.
     *
     * @return a non-null {@link IProcessRepository} instance.
     */
    IProcessRepository getProcessRepository();

    /**
     * Returns the repository responsible for {@link com.example.legal_system.model.Access} query operations.
     *
     * @return a non-null {@link IAccessRepository} instance.
     */
    IAccessRepository getAccessRepository();
}
