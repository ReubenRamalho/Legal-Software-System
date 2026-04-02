package com.example.legal_system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.legal_system.domain.ProcessObserver;
import com.example.legal_system.enums.StatusProcess;
import com.example.legal_system.model.Process;

/**
 * Subject in the <b>Observer</b> pattern for legal process status transitions.
 *
 * <p>Maintains the list of registered {@link ProcessObserver}s and notifies all
 * of them whenever a process changes its {@link StatusProcess}. Spring automatically
 * injects all beans that implement {@link ProcessObserver} into this component via
 * constructor injection, so no manual registration is necessary for Spring-managed
 * observers.</p>
 *
 * <p>Additional observers can also be registered at runtime via
 * {@link #addObserver(ProcessObserver)} for non-Spring-managed listeners.</p>
 */
@Component
public class ProcessStatusNotifier {

    private final List<ProcessObserver> observers;

    /**
     * Spring-injected constructor. All {@link ProcessObserver} beans declared in the
     * application context are automatically collected into the list.
     *
     * @param observers the list of observer beans provided by Spring.
     */
    public ProcessStatusNotifier(List<ProcessObserver> observers) {
        this.observers = new ArrayList<>(observers);
    }

    /**
     * Registers an additional observer that will be notified on future status changes.
     *
     * @param observer the observer to add; must not be {@code null}.
     */
    public void addObserver(ProcessObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes a previously registered observer.
     *
     * @param observer the observer to remove.
     */
    public void removeObserver(ProcessObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers that the given process changed its status.
     *
     * <p>Each observer's {@link ProcessObserver#onProcessStatusChanged(Process, StatusProcess)}
     * method is called in registration order. The {@code process} parameter already
     * reflects the new status at the time of notification.</p>
     *
     * @param process   the process that was updated (with the new status already set).
     * @param oldStatus the status the process held before the transition.
     */
    public void notifyObservers(Process process, StatusProcess oldStatus) {
        for (ProcessObserver observer : observers) {
            observer.onProcessStatusChanged(process, oldStatus);
        }
    }
}
