package com.example.legal_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.legal_system.process.CreateProcessDTO;
import com.example.legal_system.process.Process;
import com.example.legal_system.process.ProcessRepository;
import com.example.legal_system.usuarios.UserRepository;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processoRepository;
    @Autowired
    private UserRepository userRepository;

    public int countProcesses() {
        return (int) processoRepository.count();
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
        
        processoRepository.save(processo);
    }
}
