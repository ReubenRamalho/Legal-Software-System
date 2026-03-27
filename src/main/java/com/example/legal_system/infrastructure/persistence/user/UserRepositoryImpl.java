package com.example.legal_system.infrastructure.persistence.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.model.User;

/**
 * JPA-based implementation of {@link IUserRepository}.
 *
 * <p>Delegates all operations to {@link UserJpaRepository}, acting as an
 * anti-corruption layer that keeps the domain interface free from Spring Data
 * JPA-specific types and annotations.</p>
 */
@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return userJpaRepository.count();
    }

    @Override
    public boolean existsByLogin(String login) {
        return userJpaRepository.existsByLogin(login);
    }

    @Override
    public boolean existsByLoginAndIdNot(String login, String id) {
        return userJpaRepository.existsByLoginAndIdNot(login, id);
    }
}
