package com.example.legal_system.controller.command.user;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.dto.UserDTO;
import com.example.legal_system.service.UserService;

/**
 * Command responsible for encapsulating the user lookup by ID operation.
 */
public class FindOneUserCommand implements Command<UserDTO> {

    private final UserService userService;
    private final String id;

    public FindOneUserCommand(UserService userService, String id) {
        this.userService = userService;
        this.id = id;
    }

    @Override
    public UserDTO execute() {
        return userService.findOne(id);
    }
}
