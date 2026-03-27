package com.example.legal_system.controller.command;

/**
 * Base interface for the Command pattern.
 *
 * <p>Each business layer operation is encapsulated as a concrete object
 * implementing this interface, decoupling the invoker (the Facade) from
 * the receiver (the Services).</p>
 *
 * @param <T> the type of result produced by the command's execution.
 *            Use {@link Void} for commands that produce no return value.
 */
public interface Command<T> {

    /**
     * Executes the operation encapsulated by this command.
     *
     * @return the result of the operation, or {@code null} when {@code T} is {@link Void}.
     */
    T execute();
}
