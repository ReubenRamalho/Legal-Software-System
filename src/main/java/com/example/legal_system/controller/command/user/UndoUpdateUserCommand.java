package com.example.legal_system.controller.command.user;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.service.UserService;

/**
 * Command responsible for encapsulating the undo of the last user update.
 *
 * Delegates to {@link UserService#undoLastUpdate(String)}, which retrieves
 * the saved Memento from the Caretaker and restores the previous user state.
 */
public class UndoUpdateUserCommand implements Command<Void> {

    private final UserService userService;
    private final String id;

    public UndoUpdateUserCommand(UserService userService, String id) {
        this.userService = userService;
        this.id = id;
    }

    @Override
    public Void execute() {
        userService.undoLastUpdate(id);
        return null;
    }
}
