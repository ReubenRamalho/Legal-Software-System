package com.usuarios.bd;

import com.usuarios.entity.IUserRepository;
import com.usuarios.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements IUserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        // Remove se já existe para evitar duplicatas
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void delete(String id) {
        users.removeIf(u -> u.getId().equals(id));
    }
}
