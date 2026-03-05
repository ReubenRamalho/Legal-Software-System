package com.example.legal_system.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UserDTO;
import com.example.legal_system.enums.UserType;
import com.example.legal_system.model.User;
import com.example.legal_system.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidatorService userValidatorService;

    public UserService(UserRepository userRepository, UserValidatorService userValidatorService) {
        this.userRepository = userRepository;
        this.userValidatorService = userValidatorService;
    }

    public void create(CreateUserDTO dto) {
        userValidatorService.validateCreateUser(dto);

        UserType userType = UserType.fromInput(dto.type());

        User user = new User(
                dto.name(),
                dto.email(),
                userType.getDisplayName(),
                dto.login(),
                dto.password());

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("users_login_key")) {
                throw new IllegalArgumentException("Login já está em uso");
            }
            throw e;
        }
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getType()))
                .collect(java.util.stream.Collectors.toList());
    }

    public int countUsers() {
        return (int) userRepository.count();
    }
}
