package com.example.demo.process;

import org.springframework.stereotype.Service;

import com.example.demo.usuarios.UserRepository;

@Service
public class ProcessService {

    private final ProcessRepository processoRepository;
    private final UserRepository userRepository;

    public ProcessService(ProcessRepository processoRepository, UserRepository userRepository) {
        this.processoRepository = processoRepository;
        this.userRepository = userRepository;
    }

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
