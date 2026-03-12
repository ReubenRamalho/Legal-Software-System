package com.example.legal_system.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
    boolean existsByLogin(String login);

    boolean existsByLoginAndIdNot(String login, String id);
}
