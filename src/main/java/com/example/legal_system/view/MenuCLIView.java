package com.example.legal_system.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.example.legal_system.controller.FacadeSingletonController;
import com.example.legal_system.process.CreateProcessDTO;
import com.example.legal_system.usuarios.CreateUserDTO;
import com.example.legal_system.usuarios.UserDTO;

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
            System.out.println("4. Contar Total de Entidades");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    criarUsuario(scanner);
                    break;
                case "2":
                    criarProcesso(scanner);
                    break;
                case "3":
                    listarUsuarios();
                    break;
                case "4":
                    contarEntidades();
                    break;
                case "5":
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

    private void criarUsuario(Scanner scanner) {
        System.out.println("\n--- Novo Usuário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Tipo (ex: admin, advogado): ");
        String tipo = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            CreateUserDTO dto = new CreateUserDTO(nome, email, tipo, login, senha);
            facade.createUser(dto);
            System.out.println("✓ Usuário criado com sucesso!");
        } catch (Exception e) {
            System.out.println("✗ Erro ao criar usuário: " + e.getMessage());
        }
    }

    private void criarProcesso(Scanner scanner) {
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
            System.out.println(";) Processo criado com sucesso!");
        } catch (Exception e) {
            System.out.println("=( Erro ao criar processo: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
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
            System.out.println("✗ Erro ao listar usuários: " + e.getMessage());
        }
    }

    private void contarEntidades() {
        System.out.println("\n--- Contagem de Entidades ---");
        try {
            int total = facade.countTotalEntities();
            System.out.println("Total de entidades (Usuários + Processos) registradas no banco: " + total);
        } catch (Exception e) {
            System.out.println("✗ Erro ao contar entidades: " + e.getMessage());
        }
    }
}
