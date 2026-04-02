package com.example.legal_system.service;

import org.springframework.stereotype.Service;

import com.example.legal_system.domain.ILogger;
import com.example.legal_system.domain.IProcessRepository;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;
import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.enums.StatusProcess;
import com.example.legal_system.model.Process;

/**
 * Service responsible for legal process lifecycle operations.
 *
 * <p>Handles the creation of new legal processes and the association of lawyers
 * (users) to them. Persistence is delegated to the domain repositories obtained
 * through the {@link RepositoryFactory}.</p>
 */
@Service
public class ProcessService {

    private final IProcessRepository processRepository;
    private final IUserRepository userRepository;
    private final ILogger logger;
    private final ProcessStatusNotifier notifier;

    public ProcessService(RepositoryFactory repositoryFactory, ILogger logger,
            ProcessStatusNotifier notifier) {
        this.processRepository = repositoryFactory.getProcessRepository();
        this.userRepository = repositoryFactory.getUserRepository();
        this.logger = logger;
        this.notifier = notifier;
    }

    /**
     * Returns the total number of legal processes currently registered.
     *
     * @return the process count.
     */
    public int countProcesses() {
        return (int) processRepository.count();
    }

    /**
     * Creates and persists a new legal process from the given data transfer object.
     *
     * <p>If lawyer IDs are provided, each valid ID is resolved to a {@link com.example.legal_system.model.User}
     * and associated with the process. Unresolved IDs are silently ignored.</p>
     *
     * @param dto the creation payload containing process details and optional lawyer IDs.
     */
    public void create(CreateProcessDTO dto) {
        Process process = Process.create(
            dto.numberCnj(),
            dto.title(),
            dto.description(),
            dto.clientName(),
            dto.court(),
            dto.district()
        );

        if (dto.lawyersIds() != null && !dto.lawyersIds().isEmpty()) {
            for (String lawyerId : dto.lawyersIds()) {
                userRepository.findById(lawyerId).ifPresent(process::addLawyer);
            }
        }

        processRepository.save(process);
        logger.info("Legal process created successfully. CNJ: " + process.getNumberCnj());
    }

    /**
     * Updates the status of an existing legal process and notifies all registered
     * {@link com.example.legal_system.domain.ProcessObserver}s of the transition.
     *
     * <p>The previous status is captured before the change so that observers receive
     * full context about the transition (old → new).</p>
     *
     * @param processId the UUID string of the process to update.
     * @param newStatus the target {@link StatusProcess}.
     * @throws IllegalArgumentException if no process exists with the given ID.
     */
    public void updateProcessStatus(String processId, StatusProcess newStatus) {
        Process process = processRepository.findById(processId)
                .orElseThrow(() -> {
                    logger.warn("Status update failed: process not found. ID: " + processId);
                    return new IllegalArgumentException("Process not found");
                });

        StatusProcess oldStatus = process.getStatus();
        process.setStatus(newStatus);
        processRepository.save(process);

        logger.info("Process status updated. ID: " + processId
                + " | " + oldStatus.name() + " → " + newStatus.name());

        notifier.notifyObservers(process, oldStatus);
    }
}
