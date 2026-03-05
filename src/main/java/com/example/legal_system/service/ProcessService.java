package com.example.legal_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.model.Process;
import com.example.legal_system.repository.ProcessRepository;
import com.example.legal_system.repository.UserRepository;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private UserRepository userRepository;

    public int countProcesses() {
        return (int) processRepository.count();
    }

    public void create(CreateProcessDTO dto) {
        Process processo = new Process(
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
