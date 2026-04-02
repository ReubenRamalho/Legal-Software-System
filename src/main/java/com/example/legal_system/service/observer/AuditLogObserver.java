package com.example.legal_system.service.observer;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.ILogger;
import com.example.legal_system.domain.ProcessObserver;
import com.example.legal_system.enums.StatusProcess;
import com.example.legal_system.model.Process;

/**
 * Concrete <b>Observer</b> that logs every legal process status transition.
 *
 * <p>Registered automatically by Spring as a {@link ProcessObserver} bean.
 * The {@link com.example.legal_system.service.ProcessStatusNotifier} picks it
 * up at startup and calls {@link #onProcessStatusChanged} whenever a process
 * transitions to a new {@link StatusProcess}.</p>
 */
@Component
public class AuditLogObserver implements ProcessObserver {

    private final ILogger logger;

    public AuditLogObserver(ILogger logger) {
        this.logger = logger;
    }

    /**
     * Logs an informational audit message describing the status transition.
     *
     * @param process   the process that was updated (new status already applied).
     * @param oldStatus the status the process held before the transition.
     */
    @Override
    public void onProcessStatusChanged(Process process, StatusProcess oldStatus) {
        logger.info(
            "[AUDIT] Process status changed — ID: " + process.getId()
            + " | CNJ: " + process.getNumberCnj()
            + " | " + oldStatus.name() + " → " + process.getStatus().name()
        );
    }
}
