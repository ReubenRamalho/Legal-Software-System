package com.usuarios;

import com.usuarios.application.dto.CreateUserDTO;
import com.usuarios.application.service.UserService;
import com.usuarios.entity.User;
import com.usuarios.bd.InMemoryUserRepository;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializar repositório e serviço
        var userRepository = new InMemoryUserRepository();
        var userService = new UserService(userRepository);
        Scanner scanner = new Scanner(System.in);
        
        boolean running = true;

        while (running) {
            System.out.println("\n=== Sistema de Usuários (Arquitetura DDD) ===");
            System.out.println("1. Criar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    criarUsuario(userService, scanner);
                    break;
                case "2":
                    listarUsuarios(userService);
                    break;
                case "3":
                    System.out.println("\nAté logo!");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        
        scanner.close();
    }

    private static void criarUsuario(UserService userService, Scanner scanner) {
        System.out.println("\n--- Criar Novo Usuário ---");
        
        System.out.print("Nome: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.println("Tipo de usuário:");
        System.out.println("1. Advogado");
        System.out.println("2. Admin");
        System.out.print("Escolha: ");
        String typeOption = scanner.nextLine().trim();
        
        String type;
        if ("1".equals(typeOption)) {
            type = "advogado";
        } else if ("2".equals(typeOption)) {
            type = "admin";
        } else {
            System.out.println("Tipo inválido! Usando Advogado como padrão.");
            type = "advogado";
        }
        
        try {
            userService.createUser(new CreateUserDTO(name, email, type));
            System.out.println("✓ Usuário criado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Erro ao criar usuário: " + e.getMessage());
        }
    }

    private static void listarUsuarios(UserService userService) {
        System.out.println("\n--- Listando Todos os Usuários ---");
        List<User> users = userService.listAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            users.forEach(user -> System.out.println("• " + user));
            System.out.println("\nTotal de usuários: " + users.size());
        }
    }
}
