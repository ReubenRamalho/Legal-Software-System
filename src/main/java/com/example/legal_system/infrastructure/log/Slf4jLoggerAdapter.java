package com.example.legal_system.infrastructure.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.legal_system.domain.ILogger;

/**
 * Infrastructure adapter that bridges the domain {@link ILogger} interface
 * to the SLF4J logging framework.
 *
 * <p>Follows the Adapter pattern: the business layer depends solely on
 * {@link ILogger}, remaining decoupled from any specific logging library.</p>
 */
@Component
public class Slf4jLoggerAdapter implements ILogger {

    private final Logger logger = LoggerFactory.getLogger(Slf4jLoggerAdapter.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}