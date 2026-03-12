package com.example.legal_system.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.IProcessRepository;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;

@Component
public class JpaRepositoryFactory implements RepositoryFactory {

    private final IUserRepository userRepository;
    private final IProcessRepository processRepository;

    public JpaRepositoryFactory(IUserRepository userRepository, IProcessRepository processRepository) {
        this.userRepository = userRepository;
        this.processRepository = processRepository;
    }

    @Override
    public IUserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public IProcessRepository getProcessRepository() {
        return processRepository;
    }
}
