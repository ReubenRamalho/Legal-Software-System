package com.example.legal_system.service.observer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.IAccessRepository;
import com.example.legal_system.domain.ILogger;
import com.example.legal_system.domain.ProcessObserver;
import com.example.legal_system.domain.RepositoryFactory;
import com.example.legal_system.enums.StatusProcess;
import com.example.legal_system.model.Access;
import com.example.legal_system.model.Process;

/**
 * Concrete <b>Observer</b> that records an automatic access log entry whenever
 * a legal process is closed ({@link StatusProcess#CLOSED}).
 *
 * <p>When a process reaches its final state, this observer creates an
 * {@link Access} record attributed to the system itself (user ID
 * {@code "SYSTEM"}) and the visited page {@code "PROCESS_CLOSED"}.
 * This provides traceability for closure events without requiring manual
 * user interaction.</p>
 *
 * <p>Registered automatically by Spring as a {@link ProcessObserver} bean,
 * so the {@link com.example.legal_system.service.ProcessStatusNotifier} picks
 * it up at startup with no additional configuration.</p>
 */
@Component
public class AccessRecordObserver implements ProcessObserver {

    private static final String SYSTEM_USER_ID = "SYSTEM";
    private static final String CLOSED_PAGE    = "PROCESS_CLOSED";

    private final IAccessRepository accessRepository;
    private final ILogger logger;

    public AccessRecordObserver(RepositoryFactory repositoryFactory, ILogger logger) {
        this.accessRepository = repositoryFactory.getAccessRepository();
        this.logger = logger;
    }

    /**
     * Persists an {@link Access} audit record when the process is closed.
     * Transitions to any other status are intentionally ignored.
     *
     * @param process   the process that was updated (new status already applied).
     * @param oldStatus the status the process held before the transition.
     */
    @Override
    public void onProcessStatusChanged(Process process, StatusProcess oldStatus) {
        if (process.getStatus() != StatusProcess.CLOSED) {
            return;
        }

        Access record = Access.create(SYSTEM_USER_ID, LocalDateTime.now(), CLOSED_PAGE);
        accessRepository.save(record);

        logger.info(
            "[ACCESS] Closure access record created for process ID: " + process.getId()
            + " | CNJ: " + process.getNumberCnj()
        );
    }
}
