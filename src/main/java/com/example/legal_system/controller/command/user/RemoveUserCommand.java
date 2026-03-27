package com.example.legal_system.controller.command.user;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.service.UserService;

/**
 * Command responsible for encapsulating the user removal operation.
 */
public class RemoveUserCommand implements Command<Void> {

    private final UserService userService;
    private final String id;

    public RemoveUserCommand(UserService userService, String id) {
        this.userService = userService;
        this.id = id;
    }

    @Override
    public Void execute() {
        userService.remove(id);
        return null;
    }
}
