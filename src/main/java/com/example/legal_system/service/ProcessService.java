package com.example.legal_system.service;

import org.springframework.stereotype.Service;

import com.example.legal_system.domain.IProcessRepository;
import com.example.legal_system.domain.IUserRepository;
import com.example.legal_system.domain.RepositoryFactory;
import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.model.Process;

@Service
public class ProcessService {

    private final IProcessRepository processRepository;
    private final IUserRepository userRepository;

    public ProcessService(RepositoryFactory repositoryFactory) {
        this.processRepository = repositoryFactory.getProcessRepository();
        this.userRepository = repositoryFactory.getUserRepository();
    }

    public int countProcesses() {
        return (int) processRepository.count();
    }

    public void create(CreateProcessDTO dto) {
        Process processo = Process.create(
            dto.numberCnj(),
            dto.title(),
            dto.description(),
            dto.clientName(),
            dto.court(),
            dto.district()
        );
        
        if (dto.lawyersIds() != null && !dto.lawyersIds().isEmpty()) {
            for (String lawyerId : dto.lawyersIds()) {
                userRepository.findById(lawyerId).ifPresent(processo::addLawyer);
            }
        }
        
        processRepository.save(processo);
    }
}
