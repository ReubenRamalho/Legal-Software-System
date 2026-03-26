package com.example.legal_system.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.example.legal_system.controller.FacadeSingletonController;
import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.dto.UserDTO;

@Component
public class MenuCLIView {

    private final FacadeSingletonController facade;

    public MenuCLIView(FacadeSingletonController facade) {
        this.facade = facade;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==================================================");
        System.out.println("  SISTEMA JURÍDICO INICIADO COM SUCESSO!  ");
        System.out.println("==================================================");

        while (running) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Criar Usuário");
            System.out.println("2. Criar Processo");
            System.out.println("3. Listar Todos os Usuários");
            System.out.println("4. Buscar Usuário por ID");
            System.out.println("5. Atualizar Usuário");
            System.out.println("6. Remover Usuário");
            System.out.println("7. Contar Total de Entidades");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    createUser(scanner);
                    break;
                case "2":
                    createProcess(scanner);
                    break;
                case "3":
                    listUsers();
                    break;
                case "4":
                    findOneUser(scanner);
                    break;
                case "5":
                    updateUser(scanner);
                    break;
                case "6":
                    removeUser(scanner);
                    break;
                case "7":
                    countEntities();
                    break;
                case "8":
                    System.out.println("\nEncerrando o sistema. Até logo!");
                    running = false;
                    break;
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        }

        scanner.close();
        System.exit(0);
    }

    private void createUser(Scanner scanner) {
        System.out.println("\n--- Novo Usuário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Tipo (Sócio-Administrador, Sócio, Advogado, Estagiário): ");
        String tipo = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        try {
            CreateUserDTO dto = new CreateUserDTO(nome, email, tipo, login, password);
            facade.createUser(dto);
            System.out.println("[OK] Usuário criado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Erro ao criar usuário: " + e.getMessage());
        }
    }

    private void createProcess(Scanner scanner) {
        System.out.println("\n--- Novo Processo ---");
        System.out.print("Número CNJ: ");
        String cnj = scanner.nextLine();

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descrição: ");
        String desc = scanner.nextLine();

        System.out.print("Nome do Cliente: ");
        String cliente = scanner.nextLine();

        System.out.print("Vara (Court): ");
        String vara = scanner.nextLine();

        System.out.print("Distrito (District): ");
        String distrito = scanner.nextLine();

        System.out.print("IDs dos Advogados (separados por vírgula ou deixe em branco): ");
        String idsInput = scanner.nextLine();

        // Converte a string de IDs separados por vírgula em uma lista
        List<String> advogadosIds = idsInput.trim().isEmpty() ? List.of() : Arrays.asList(idsInput.split("\\s*,\\s*"));

        try {
            CreateProcessDTO dto = new CreateProcessDTO(cnj, titulo, desc, cliente, vara, distrito, advogadosIds);
            facade.createProcess(dto);
            System.out.println("[OK] Processo criado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Erro ao criar processo: " + e.getMessage());
        }
    }

    private void listUsers() {
        System.out.println("\n--- Lista de Usuários ---");
        try {
            List<UserDTO> users = facade.findAllUsers();
            if (users.isEmpty()) {
                System.out.println("Nenhum usuário cadastrado.");
            } else {
                for (UserDTO user : users) {
                    System.out.println("- " + user.id() + ": " + user.name() + " (Tipo: " + user.type() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Erro ao listar usuários: " + e.getMessage());
        }
    }

    private void findOneUser(Scanner scanner) {
        System.out.println("\n--- Buscar Usuário por ID ---");
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine().trim();

        try {
            UserDTO user = facade.findOneUser(id);
            System.out.println("Usuário encontrado:");
            System.out.println("- ID: " + user.id());
            System.out.println("- Nome: " + user.name());
            System.out.println("- Tipo: " + user.type());
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Erro ao buscar usuário: " + e.getMessage());
        }
    }

    private void updateUser(Scanner scanner) {
        System.out.println("\n--- Atualizar Usuário ---");
        System.out.println("(Deixe em branco para manter o valor atual)");
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine().trim();

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        System.out.print("Novo tipo (Sócio-Administrador, Sócio, Advogado, Estagiário): ");
        String tipo = scanner.nextLine();

        System.out.print("Novo login: ");
        String login = scanner.nextLine();

        System.out.print("Nova senha: ");
        String password = scanner.nextLine();

        try {
            UpdateUserDTO dto = new UpdateUserDTO(nome, email, tipo, login, password);
            facade.updateUser(id, dto);
            System.out.println("[OK] Usuário atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    private void removeUser(Scanner scanner) {
        System.out.println("\n--- Remover Usuário ---");
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine().trim();

        try {
            facade.removeUser(id);
            System.out.println("[OK] Usuário removido com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Erro ao remover usuário: " + e.getMessage());
        }
    }

    private void countEntities() {
        System.out.println("\n--- Contagem de Entidades ---");
        try {
            int total = facade.countTotalEntities();
            System.out.println("Total de entidades (Usuários + Processos) registradas no banco: " + total);
        } catch (Exception e) {
            System.out.println("[ERROR] Erro ao contar entidades: " + e.getMessage());
        }
    }
}
