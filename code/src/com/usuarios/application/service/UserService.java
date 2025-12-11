package com.usuarios.application.service;

import com.usuarios.application.dto.CreateUserDTO;
import com.usuarios.domain.entity.IUserRepository;
import com.usuarios.domain.entity.User;
import java.util.List;

public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário não pode estar vazio");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do usuário não pode estar vazio");
        }
        if (dto.getType() == null || dto.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do usuário não pode estar vazio");
        }

        User user = new User(dto.getName(), dto.getEmail(), dto.getType());
        userRepository.save(user);
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
}
