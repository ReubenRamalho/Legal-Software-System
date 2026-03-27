package com.example.legal_system.enums;

/**
 * Represents the possible lifecycle states of a legal process.
 *
 * <ul>
 *   <li>{@link #ACTIVE}     — The process is currently in progress.</li>
 *   <li>{@link #SUSPENDED}  — The process has been temporarily halted.</li>
 *   <li>{@link #ARCHIVED}   — The process has been archived for record-keeping.</li>
 *   <li>{@link #CLOSED}     — The process has been concluded.</li>
 *   <li>{@link #IN_APPEAL}  — The process is under appeal.</li>
 * </ul>
 */
public enum StatusProcess {
    ACTIVE,
    SUSPENDED,
    ARCHIVED,
    CLOSED,
    IN_APPEAL
}
