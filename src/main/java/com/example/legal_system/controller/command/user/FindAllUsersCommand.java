package com.example.legal_system.controller.command.user;

import java.util.List;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.dto.UserDTO;
import com.example.legal_system.service.UserService;

/**
 * Command responsável por encapsular a operação de listagem de todos os usuários.
 */
public class FindAllUsersCommand implements Command<List<UserDTO>> {

    private final UserService userService;

    public FindAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDTO> execute() {
        return userService.findAll();
    }
}
