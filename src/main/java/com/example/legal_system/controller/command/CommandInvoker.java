package com.example.legal_system.controller.command;

/**
 * Invoker of the Command pattern.
 *
 * <p>Responsible for receiving a {@link Command} object and triggering its execution.
 * Completely decouples the request sender (the Facade) from the receiver that
 * actually executes the business logic (the Services).</p>
 *
 * <p>The Facade does not need to know how a Command was constructed or how it
 * works internally — it simply hands the Command to the Invoker and receives
 * the result in return.</p>
 */
public class CommandInvoker {

    /**
     * Executes the given Command and returns its result.
     *
     * @param <T>     the type of result produced by the Command.
     * @param command the Command to execute.
     * @return the result returned by the Command.
     */
    public <T> T invoke(Command<T> command) {
        return command.execute();
    }
}
