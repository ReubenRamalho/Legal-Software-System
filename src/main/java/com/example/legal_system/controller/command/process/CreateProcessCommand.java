package com.example.legal_system.controller.command.process;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.service.ProcessService;

/**
 * Command responsible for encapsulating the process creation operation.
 */
public class CreateProcessCommand implements Command<Void> {

    private final ProcessService processService;
    private final CreateProcessDTO dto;

    public CreateProcessCommand(ProcessService processService, CreateProcessDTO dto) {
        this.processService = processService;
        this.dto = dto;
    }

    @Override
    public Void execute() {
        processService.create(dto);
        return null;
    }
}
