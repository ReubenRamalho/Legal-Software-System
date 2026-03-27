package com.example.legal_system.controller.command.process;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.service.ProcessService;
import com.example.legal_system.service.UserService;

/**
 * Command responsible for encapsulating the total entity count operation
 * (users + processes).
 */
public class CountEntitiesCommand implements Command<Integer> {

    private final UserService userService;
    private final ProcessService processService;

    public CountEntitiesCommand(UserService userService, ProcessService processService) {
        this.userService = userService;
        this.processService = processService;
    }

    @Override
    public Integer execute() {
        return userService.countUsers() + processService.countProcesses();
    }
}
