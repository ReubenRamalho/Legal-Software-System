package com.example.legal_system.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.IAcessoRepository;
import com.example.legal_system.domain.IProcessRepository;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;

@Component
public class JpaRepositoryFactory implements RepositoryFactory {

    private final IUserRepository userRepository;
    private final IProcessRepository processRepository;
    private final IAcessoRepository acessoRepository;

    public JpaRepositoryFactory(IUserRepository userRepository, 
                                IProcessRepository processRepository,
                                IAcessoRepository acessoRepository) {
        this.userRepository = userRepository;
        this.processRepository = processRepository;
        this.acessoRepository = acessoRepository;
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
    public IAcessoRepository getAcessoRepository() {
        return acessoRepository;
    }
}
