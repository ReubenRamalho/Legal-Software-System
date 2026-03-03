package com.example.demo.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.usuarios.UserRepository;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;
    
    @Autowired
    private UserRepository userRepository;

    public int countProcesses() {
        return (int) processoRepository.count();
    }

    public void create(CreateProcessoDTO dto) {
        Processo processo = new Processo(
            dto.getNumberCnj(),
            dto.getTitle(),
            dto.getDescription(),
            dto.getClientName(),
            dto.getCourt(),
            dto.getDistrict()
        );
        
        if (dto.getLawyersIds() != null && !dto.getLawyersIds().isEmpty()) {
            for (String lawyerId : dto.getLawyersIds()) {
                userRepository.findById(lawyerId).ifPresent(processo::addLawyer);
            }
        }
        
        processoRepository.save(processo);
    }
}
