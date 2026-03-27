package com.example.legal_system.controller.command.user;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.service.UserService;

/**
 * Command responsible for encapsulating the user update operation.
 */
public class UpdateUserCommand implements Command<Void> {

    private final UserService userService;
    private final String id;
    private final UpdateUserDTO dto;

    public UpdateUserCommand(UserService userService, String id, UpdateUserDTO dto) {
        this.userService = userService;
        this.id = id;
        this.dto = dto;
    }

    @Override
    public Void execute() {
        userService.update(id, dto);
        return null;
    }
}
