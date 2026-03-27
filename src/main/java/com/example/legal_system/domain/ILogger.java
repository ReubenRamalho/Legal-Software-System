package com.example.legal_system.domain;

/**
 * Abstraction for application-level logging.
 *
 * <p>Decouples the business layer from any concrete logging framework
 * (e.g., SLF4J, Log4j). Implementations are provided by the infrastructure layer.</p>
 */
public interface ILogger {

    /**
     * Logs an informational message.
     *
     * @param message the message to log.
     */
    void info(String message);

    /**
     * Logs a warning message indicating a potentially harmful situation.
     *
     * @param message the message to log.
     */
    void warn(String message);

    /**
     * Logs an error message along with the exception that caused it.
     *
     * @param message   the message to log.
     * @param throwable the exception associated with the error.
     */
    void error(String message, Throwable throwable);
}