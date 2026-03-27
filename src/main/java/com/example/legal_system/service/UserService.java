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
import com.example.legal_system.memento.UserMementoCaretaker;
import com.example.legal_system.model.User;

/**
 * Service responsible for all user lifecycle operations.
 *
 * <p>Orchestrates creation, retrieval, update, removal, and undo of user records.
 * Business rule validation is delegated to {@link UserValidatorService}, while
 * state snapshot management for undo support uses the Memento pattern via
 * {@link UserMementoCaretaker}.</p>
 */
@Service
public class UserService {

    private final IUserRepository userRepository;
    private final UserValidatorService userValidatorService;
    private final ILogger logger;
    private final UserMementoCaretaker caretaker;

    public UserService(
            RepositoryFactory repositoryFactory,
            UserValidatorService userValidatorService,
            ILogger logger,
            UserMementoCaretaker caretaker) {
        this.userRepository = repositoryFactory.getUserRepository();
        this.userValidatorService = userValidatorService;
        this.logger = logger;
        this.caretaker = caretaker;
    }

    /**
     * Creates and persists a new user from the given data transfer object.
     *
     * @param dto the creation payload.
     * @throws IllegalArgumentException      if validation fails.
     * @throws DataIntegrityViolationException if the login is already taken at the database level.
     */
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
            logger.info("User created successfully. Login: " + user.getLogin());
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("users_login_key")) {
                logger.error("Failed to create user: login already in use. Login: " + dto.login(), e);
                throw new IllegalArgumentException("Login is already in use");
            }
            throw e;
        }
    }

    /**
     * Returns a list of all users in the system as DTOs.
     *
     * @return a list of {@link UserDTO}, never {@code null}.
     */
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Finds a single user by ID and returns it as a DTO.
     *
     * @param id the UUID string of the user.
     * @return the corresponding {@link UserDTO}.
     * @throws IllegalArgumentException if no user exists with the given ID.
     */
    public UserDTO findOne(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User lookup failed: user not found. ID: " + id);
                    return new IllegalArgumentException("User not found");
                });

        logger.info("User retrieved successfully. ID: " + id);
        return toDTO(user);
    }

    /**
     * Updates a user's fields with the non-null values from the given DTO.
     *
     * <p>Before applying any changes, the current user state is saved as a Memento
     * so the update can be undone via {@link #undoLastUpdate(String)}.</p>
     *
     * @param id  the UUID string of the user to update.
     * @param dto the partial update payload (null fields are ignored).
     * @throws IllegalArgumentException if the user is not found or validation fails.
     */
    public void update(String id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User update failed: user not found. ID: " + id);
                    return new IllegalArgumentException("User not found");
                });

        UpdateUserDTO normalizedDto = dto.normalized();
        userValidatorService.validateUpdateUser(id, normalizedDto);

        // Memento: capture current state before applying any changes
        caretaker.save(user.createMemento());

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
            logger.info("User updated successfully. ID: " + id);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("users_login_key")) {
                logger.error("Failed to update user: login already in use. ID: " + id, e);
                throw new IllegalArgumentException("Login is already in use");
            }
            throw e;
        }
    }

    /**
     * Undoes the last update applied to the given user.
     *
     * <p>Retrieves the Memento saved by the Caretaker, restores the previous state
     * on the {@link User} Originator, persists the reverted data, and clears the
     * Memento to prevent a second undo.</p>
     *
     * @param id the UUID string of the user whose last update should be undone.
     * @throws IllegalArgumentException if no update has been recorded for this user
     *                                  or the user does not exist.
     */
    public void undoLastUpdate(String id) {
        var memento = caretaker.getLast(id)
                .orElseThrow(() -> {
                    logger.warn("Undo failed: no recorded update for user. ID: " + id);
                    return new IllegalArgumentException("No update to undo");
                });

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Undo failed: user not found. ID: " + id);
                    return new IllegalArgumentException("User not found");
                });

        user.restoreFromMemento(memento);
        userRepository.save(user);
        caretaker.clear(id);

        logger.info("Last update undone successfully. ID: " + id);
    }

    /**
     * Removes the user with the given ID from the system.
     *
     * @param id the UUID string of the user to delete.
     * @throws IllegalArgumentException if no user exists with the given ID.
     */
    public void remove(String id) {
        if (userRepository.findById(id).isEmpty()) {
            logger.warn("User removal failed: user not found. ID: " + id);
            throw new IllegalArgumentException("User not found");
        }

        userRepository.deleteById(id);
        logger.info("User removed successfully. ID: " + id);
    }

    /**
     * Returns the total number of users currently registered.
     *
     * @return the user count.
     */
    public int countUsers() {
        return (int) userRepository.count();
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getType());
    }
}
