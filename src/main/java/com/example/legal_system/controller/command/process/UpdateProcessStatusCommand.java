package com.example.legal_system.controller.command.process;

import com.example.legal_system.controller.command.Command;
import com.example.legal_system.enums.StatusProcess;
import com.example.legal_system.service.ProcessService;

/**
 * Command that updates the status of a legal process.
 *
 * <p>Encapsulates a single call to {@link ProcessService#updateProcessStatus(String, StatusProcess)},
 * triggering the Observer notification chain for all registered
 * {@link com.example.legal_system.domain.ProcessObserver}s.</p>
 */
public class UpdateProcessStatusCommand implements Command<Void> {

    private final ProcessService processService;
    private final String processId;
    private final StatusProcess newStatus;

    public UpdateProcessStatusCommand(ProcessService processService, String processId,
            StatusProcess newStatus) {
        this.processService = processService;
        this.processId = processId;
        this.newStatus = newStatus;
    }

    @Override
    public Void execute() {
        processService.updateProcessStatus(processId, newStatus);
        return null;
    }
}
