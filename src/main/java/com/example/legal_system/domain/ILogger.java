package com.example.legal_system.domain;

public interface ILogger {

    void info(String message);

    void warn(String message);

    void error(String message, Throwable throwable);
}