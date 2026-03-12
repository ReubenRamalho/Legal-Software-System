package com.example.legal_system.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.dto.UserDTO;
import com.example.legal_system.service.ProcessService;
import com.example.legal_system.service.UserService;

@Component
public class FacadeSingletonController {

    private final UserService userService;
    private final ProcessService processService;

    public FacadeSingletonController(UserService userService, ProcessService processService) {
        this.userService = userService;
        this.processService = processService;
    }

    public int countTotalEntities() {
        return userService.countUsers() + processService.countProcesses();
    }

    public void createUser(CreateUserDTO dto) {
        userService.create(dto);
    }

    public List<UserDTO> findAllUsers() {
        return userService.findAll();
    }

    public UserDTO findOneUser(String id) {
        return userService.findOne(id);
    }

    public void updateUser(String id, UpdateUserDTO dto) {
        userService.update(id, dto);
    }

    public void removeUser(String id) {
        userService.remove(id);
    }

    public void createProcess(CreateProcessDTO dto) {
        processService.create(dto);
    }
}
