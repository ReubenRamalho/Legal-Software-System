package com.example.legal_system.domain;

import java.util.List;
import java.util.Optional;

import com.example.legal_system.model.User;

public interface IUserRepository {
    User save(User user);

    Optional<User> findById(String id);

    List<User> findAll();

    void deleteById(String id);

    long count();

    boolean existsByLogin(String login);

    boolean existsByLoginAndIdNot(String login, String id);
}
