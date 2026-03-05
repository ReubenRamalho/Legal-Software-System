package com.example.legal_system.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.legal_system.process.CreateProcessDTO;
import com.example.legal_system.service.ProcessService;
import com.example.legal_system.usuarios.CreateUserDTO;
import com.example.legal_system.usuarios.UserDTO;
import com.example.legal_system.service.UserService;

@Component
public class FacadeSingletonController {

    // O atributo estático não é mais necessário porque o Spring guardará
    // a referência da instância única no seu próprio Contexto (ApplicationContext).
    // private static FacadeSingletonController instance;

    private final UserService userService;
    private final ProcessService processService;

    // O Spring se encarrega de instanciar os Services e injetar o Repositório dentro deles.
    public FacadeSingletonController(UserService userService, ProcessService processService) {
        this.userService = userService;
        this.processService = processService;
    }

    // O método getInstance() e o "synchronized" tornam-se redundantes.
    // Qualquer classe que precisar usar a Fachada não vai chamar "getInstance()",
    // mas sim solicitar que o Spring a injete via construtor (Injeção de Dependência).
    // public static synchronized FacadeSingletonController getInstance() {
    //     if (instance == null) {
    //         instance = new FacadeSingletonController();
    //     }
    //     return instance;
    // }

    public int countTotalEntities() {
        return userService.countUsers() + processService.countProcesses();
    }

    public void createUser(CreateUserDTO dto) {
        userService.create(dto);
    }

    public List<UserDTO> findAllUsers() {
        return userService.findAll();
    }

    public void createProcess(CreateProcessDTO dto) {
        processService.create(dto);
    }
}
