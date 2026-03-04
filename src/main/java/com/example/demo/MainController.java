package com.example.demo;

import java.util.List;

import com.example.demo.process.CreateProcessDTO;
import com.example.demo.usuarios.CreateUserDTO;

public class MainController {

    public static void main(String[] args) {

        FacadeSingletonController facade = FacadeSingletonController.getInstance();

        CreateUserDTO newUserDTO = new CreateUserDTO(
                "João Silva",
                "joao.silva@example.com",
                "admin",
                "joaosilva",
                "Senha@123");
        facade.createUser(newUserDTO);

        CreateProcessDTO newProcessDTO = new CreateProcessDTO(
                "123456",
                "Título do Processo",
                "Descrição do processo",
                "Cliente Exemplo",
                "Vara Cível",
                "Distrito Exemplo",
                List.of("1", "2")
        );
        facade.createProcess(newProcessDTO);

        int totalEntities = facade.countTotalEntities();
        System.out.println("Total de entidades registradas no sistema: " + totalEntities);
    }

}
