package com.example.legal_system.domain;

public interface RepositoryFactory {
    IUserRepository getUserRepository();

    IProcessRepository getProcessRepository();
}
