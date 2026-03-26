package com.example.legal_system.domain;

import com.example.legal_system.model.Process;

public interface IProcessRepository {
    Process save(Process process);

    long count();
}
