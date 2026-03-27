package com.example.legal_system.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.domain.IProcessRepository;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;

/**
 * JPA-based implementation of the {@link RepositoryFactory} abstract factory.
 *
 * <p>Wires together the Spring Data JPA repository adapters and exposes them
 * through the domain interface, keeping the business layer unaware of JPA details.</p>
 */
@Component
public class JpaRepositoryFactory implements RepositoryFactory {

    private final IUserRepository userRepository;
    private final IProcessRepository processRepository;
    private final IAccessRepository accessRepository;

    public JpaRepositoryFactory(IUserRepository userRepository,
                                IProcessRepository processRepository,
                                IAccessRepository accessRepository) {
        this.userRepository = userRepository;
        this.processRepository = processRepository;
        this.accessRepository = accessRepository;
    }

    @Override
    public IUserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public IProcessRepository getProcessRepository() {
        return processRepository;
    }

    @Override
    public IAccessRepository getAccessRepository() {
        return accessRepository;
    }
}
