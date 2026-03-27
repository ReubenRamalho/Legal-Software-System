package com.example.legal_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.legal_system.controller.command.CommandInvoker;
import com.example.legal_system.controller.command.process.CountEntitiesCommand;
import com.example.legal_system.controller.command.process.CreateProcessCommand;
import com.example.legal_system.controller.command.report.GenerateAccessReportCommand;
import com.example.legal_system.controller.command.user.CreateUserCommand;
import com.example.legal_system.controller.command.user.FindAllUsersCommand;
import com.example.legal_system.controller.command.user.FindOneUserCommand;
import com.example.legal_system.controller.command.user.RemoveUserCommand;
import com.example.legal_system.controller.command.user.UndoUpdateUserCommand;
import com.example.legal_system.controller.command.user.UpdateUserCommand;
import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.dto.UserDTO;
import com.example.legal_system.service.AccessReportService;
import com.example.legal_system.service.ProcessService;
import com.example.legal_system.service.UserService;

/**
 * Business layer facade — implements the Command pattern.
 *
 * <p>Each public method constructs the {@link Command} corresponding to the requested
 * operation and delegates its execution to the {@link CommandInvoker}. The Facade
 * acts as the Client in the pattern: it knows which Commands exist and how to build
 * them, but it never executes any business logic directly.</p>
 */
@Component
public class FacadeSingletonController {

    private final UserService userService;
    private final ProcessService processService;
    private final AccessReportService accessReportService;
    private final CommandInvoker invoker;

    public FacadeSingletonController(
            UserService userService,
            ProcessService processService,
            AccessReportService accessReportService) {
        this.userService = userService;
        this.processService = processService;
        this.accessReportService = accessReportService;
        this.invoker = new CommandInvoker();
    }

    public int countTotalEntities() {
        return invoker.invoke(new CountEntitiesCommand(userService, processService));
    }

    public void createUser(CreateUserDTO dto) {
        invoker.invoke(new CreateUserCommand(userService, dto));
    }

    public List<UserDTO> findAllUsers() {
        return invoker.invoke(new FindAllUsersCommand(userService));
    }

    public UserDTO findOneUser(String id) {
        return invoker.invoke(new FindOneUserCommand(userService, id));
    }

    public void updateUser(String id, UpdateUserDTO dto) {
        invoker.invoke(new UpdateUserCommand(userService, id, dto));
    }

    public void undoUpdateUser(String id) {
        invoker.invoke(new UndoUpdateUserCommand(userService, id));
    }

    public void removeUser(String id) {
        invoker.invoke(new RemoveUserCommand(userService, id));
    }

    public void createProcess(CreateProcessDTO dto) {
        invoker.invoke(new CreateProcessCommand(processService, dto));
    }

    public String generateAccessReport(String format, LocalDate startDate, LocalDate endDate) {
        return invoker.invoke(new GenerateAccessReportCommand(accessReportService, format, startDate, endDate));
    }
}
