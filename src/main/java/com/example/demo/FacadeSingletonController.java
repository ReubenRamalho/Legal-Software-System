package com.example.demo;

import com.example.demo.process.CreateProcessDTO;
import com.example.demo.process.ProcessService;
import com.example.demo.usuarios.CreateUserDTO;
import com.example.demo.usuarios.UserService;

public class FacadeSingletonController {

    private static FacadeSingletonController instance;
    
    private final UserService userService;
    private final ProcessService processService;

    private FacadeSingletonController() {
        this.userService = new UserService();
        this.processService = new ProcessService();
    }

    public static synchronized FacadeSingletonController getInstance() {
        if (instance == null) {
            instance = new FacadeSingletonController();
        }
        return instance;
    }

    public int countTotalEntities() {
        return userService.countUsers() + processService.countProcesses();
    }

    void createUser(CreateUserDTO dto) {
        userService.createUser(dto);
    }

    void createProcess(CreateProcessDTO dto) {
        processService.create(dto);
    }
}
