package com.example.legal_system.controller.command.user;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.service.UserService;

/**
 * Command responsável por encapsular a operação de criação de usuário.
 */
public class CreateUserCommand implements Command<Void> {

    private final UserService userService;
    private final CreateUserDTO dto;

    public CreateUserCommand(UserService userService, CreateUserDTO dto) {
        this.userService = userService;
        this.dto = dto;
    }

    @Override
    public Void execute() {
        userService.create(dto);
        return null;
    }
}
