package com.example.legal_system.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.legal_system.domain.ILogger;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.dto.UserDTO;
import com.example.legal_system.enums.UserType;
import com.example.legal_system.model.User;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final UserValidatorService userValidatorService;
    private final ILogger logger;

    public UserService(
            RepositoryFactory repositoryFactory,
            UserValidatorService userValidatorService,
            ILogger logger) {
        this.userRepository = repositoryFactory.getUserRepository();
        this.userValidatorService = userValidatorService;
        this.logger = logger;
    }

    public void create(CreateUserDTO dto) {
        userValidatorService.validateCreateUser(dto);

        UserType userType = UserType.fromInput(dto.type());

        User user = User.create(
                dto.name(),
                dto.email(),
                userType.getDisplayName(),
                dto.login(),
                dto.password());

        try {
            userRepository.save(user);
            logger.info("Usuário criado com sucesso. Login: " + user.getLogin());
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("users_login_key")) {
                logger.error("Falha ao criar usuário: login já está em uso. Login: " + dto.login(), e);
                throw new IllegalArgumentException("Login já está em uso");
            }
            throw e;
        }
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public UserDTO findOne(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Consulta de usuário falhou: usuário não encontrado. ID: " + id);
                    return new IllegalArgumentException("Usuário não encontrado");
                });

        logger.info("Usuário consultado com sucesso. ID: " + id);

        return toDTO(user);
    }

    public void update(String id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Atualização de usuário falhou: usuário não encontrado. ID: " + id);
                    return new IllegalArgumentException("Usuário não encontrado");
                });
        UpdateUserDTO normalizedDto = dto.normalized();

        userValidatorService.validateUpdateUser(id, normalizedDto);

        String name = normalizedDto.name();
        String email = normalizedDto.email();
        String type = normalizedDto.type();
        String login = normalizedDto.login();
        String password = normalizedDto.password();

        if (name != null) {
            user.setName(name);
        }

        if (email != null) {
            user.setEmail(email);
        }

        if (type != null) {
            UserType userType = UserType.fromInput(type);
            user.setType(userType.getDisplayName());
        }

        if (login != null) {
            user.setLogin(login);
        }

        if (password != null) {
            user.setPassword(password);
        }

        try {
            userRepository.save(user);
            logger.info("Usuário atualizado com sucesso. ID: " + id);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("users_login_key")) {
                logger.error("Falha ao atualizar usuário: login já está em uso. ID: " + id, e);
                throw new IllegalArgumentException("Login já está em uso");
            }
            throw e;
        }
    }

    public void remove(String id) {
        if (userRepository.findById(id).isEmpty()) {
            logger.warn("Remoção de usuário falhou: usuário não encontrado. ID: " + id);
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        userRepository.deleteById(id);
        logger.info("Usuário removido com sucesso. ID: " + id);
    }

    public int countUsers() {
        return (int) userRepository.count();
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getType());
    }
}
