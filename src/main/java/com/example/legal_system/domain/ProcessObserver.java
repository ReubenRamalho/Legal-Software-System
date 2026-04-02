package com.example.legal_system.domain;

import com.example.legal_system.enums.StatusProcess;
import com.example.legal_system.model.Process;

/**
 * Observer interface for the Observer pattern applied to legal process status changes.
 *
 * <p>Any component interested in being notified when a {@link Process} changes its
 * {@link StatusProcess} must implement this interface and register with the
 * {@link com.example.legal_system.service.ProcessStatusNotifier}.</p>
 *
 * <p>Concrete implementations include:
 * <ul>
 *   <li>{@link com.example.legal_system.service.observer.AuditLogObserver} — logs the transition.</li>
 *   <li>{@link com.example.legal_system.service.observer.AccessRecordObserver} — records an audit access entry.</li>
 * </ul>
 * </p>
 */
public interface ProcessObserver {

    /**
     * Called when a process transitions to a new status.
     *
     * @param process   the process whose status changed (already reflects the new status).
     * @param oldStatus the status the process held before the transition.
     */
    void onProcessStatusChanged(Process process, StatusProcess oldStatus);
}
