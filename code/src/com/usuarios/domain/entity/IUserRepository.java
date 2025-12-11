package com.usuarios.domain.entity;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    void save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    void delete(String id);
}
