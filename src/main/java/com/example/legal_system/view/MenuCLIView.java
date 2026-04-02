package com.example.legal_system.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.legal_system.controller.FacadeSingletonController;
import com.example.legal_system.dto.CreateProcessDTO;
import com.example.legal_system.dto.CreateUserDTO;
import com.example.legal_system.dto.UpdateUserDTO;
import com.example.legal_system.dto.UserDTO;

/**
 * Command-line interface view for the Legal System.
 *
 * <p>Presents an interactive text menu to the user and translates input into
 * calls to the {@link FacadeSingletonController}. All user-facing labels are
 * intentionally kept in Portuguese, as the target users are Brazilian legal
 * professionals.</p>
 */
@Component
public class MenuCLIView {

    private final FacadeSingletonController facade;

    public MenuCLIView(FacadeSingletonController facade) {
        this.facade = facade;
    }

    /**
     * Starts the main menu loop, reading user input until the exit option is selected.
     */
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
            System.out.println("8. Gerar Relatório de Acessos");
            System.out.println("9. Desfazer Última Atualização de Usuário");
            System.out.println("10. Atualizar Status do Processo");
            System.out.println("11. Sair");
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
                    generateReport(scanner);
                    break;
                case "9":
                    undoUpdateUser(scanner);
                    break;
                case "10":
                    updateProcessStatus(scanner);
                    break;
                case "11":
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
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Tipo (Sócio-Administrador, Sócio, Advogado, Estagiário): ");
        String type = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        try {
            CreateUserDTO dto = new CreateUserDTO(name, email, type, login, password);
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
        String title = scanner.nextLine();

        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        System.out.print("Nome do Cliente: ");
        String clientName = scanner.nextLine();

        System.out.print("Vara (Court): ");
        String court = scanner.nextLine();

        System.out.print("Distrito (District): ");
        String district = scanner.nextLine();

        System.out.print("IDs dos Advogados (separados por vírgula ou deixe em branco): ");
        String idsInput = scanner.nextLine();

        // Parse the comma-separated list of lawyer IDs; empty input yields an empty list
        List<String> lawyerIds = idsInput.trim().isEmpty()
                ? List.of()
                : Arrays.asList(idsInput.split("\\s*,\\s*"));

        try {
            CreateProcessDTO dto = new CreateProcessDTO(cnj, title, description, clientName, court, district, lawyerIds);
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
        String name = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        System.out.print("Novo tipo (Sócio-Administrador, Sócio, Advogado, Estagiário): ");
        String type = scanner.nextLine();

        System.out.print("Novo login: ");
        String login = scanner.nextLine();

        System.out.print("Nova senha: ");
        String password = scanner.nextLine();

        try {
            UpdateUserDTO dto = new UpdateUserDTO(name, email, type, login, password);
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

    private void generateReport(Scanner scanner) {
        System.out.println("\n--- Gerar Relatório de Acessos ---");
        System.out.print("Formato desejado (HTML ou PDF): ");
        String format = scanner.nextLine().trim();

        try {
            // Fixed period example: last 30 days up to today
            LocalDate today = LocalDate.now();
            LocalDate lastMonth = today.minusDays(30);

            System.out.println("Gerando relatório para o período: " + lastMonth + " a " + today + "...");

            String savedPath = facade.generateAccessReport(format, lastMonth, today);

            System.out.println("[OK] Relatório gerado com sucesso!");
            System.out.println("O arquivo foi salvo em: " + savedPath);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Formato inválido ou erro de regra de negócio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Erro inesperado ao gerar relatório: " + e.getMessage());
        }
    }

    private void undoUpdateUser(Scanner scanner) {
        System.out.println("\n--- Desfazer Última Atualização de Usuário ---");
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine().trim();

        try {
            facade.undoUpdateUser(id);
            System.out.println("[OK] Última atualização desfeita com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Não foi possível desfazer: " + e.getMessage());
        }
    }

    private void updateProcessStatus(Scanner scanner) {
        System.out.println("\n--- Atualizar Status do Processo ---");
        System.out.print("ID do processo: ");
        String id = scanner.nextLine().trim();

        System.out.println("Status disponíveis: ACTIVE, SUSPENDED, ARCHIVED, CLOSED, IN_APPEAL");
        System.out.print("Novo status: ");
        String status = scanner.nextLine().trim();

        try {
            facade.updateProcessStatus(id, status);
            System.out.println("[OK] Status do processo atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Erro ao atualizar status: " + e.getMessage());
        }
    }
}
