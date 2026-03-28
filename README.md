# Legal Software System

**Sistema Jurídico** — Aplicação para gerenciar usuários, processos e documentação jurídica

Projeto da disciplina **Métodos de Projeto de Software** do Professor Raoni

## Participantes

- Antônio Augusto Dantas Neto - 20230012215
- Deivily Breno Silva Carneiro - 20230012734
- Lucas Gabriel Fontes da Silva - 20230012592
- Rafael de França Silva - 20230012654
- Reuben Lisboa Ramalho Claudino - 20210024602
- Tobias Freire Numeriano - 20230012378

## Tecnologias Utilizadas

- **Java 21** — Linguagem de programação
- **Spring Boot 3.5.11** — Framework web
- **Spring Data JPA** — Persistência de dados
- **PostgreSQL 15** — Banco de dados relacional
- **Flyway** — Migrations de banco de dados
- **Lombok** — Redução de boilerplate
- **Maven 3.8+** — Gerenciador de dependências
- **Docker** — Containerização do banco de dados

## Pré-requisitos

- **JDK 21+** → `java -version`
- **Maven 3.8+** → `mvn -version`
- **Docker** → `docker --version`

## Como Rodar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/ReubenRamalho/Legal-Software-System.git
cd Legal-Software-System
```

### 2. Suba o banco de dados PostgreSQL

```bash
docker-compose up -d
```

**Credenciais padrão:** Host `localhost:5432` | Database `db_mps` | User `user` | Password `password`

### 3. Compile o projeto

```bash
mvn clean compile
```

### 4. Execute a aplicação

```bash
mvn spring-boot:run
```

A aplicação irá iniciar e exibir o menu interativo no terminal.

## Estrutura do Projeto

```
src/main/java/com/example/legal_system/
├── controller/          # FacadeSingletonController e Commands
│   └── command/         # Command, CommandInvoker e implementações
├── domain/              # Interfaces de repositório e RepositoryFactory
├── dto/                 # Data Transfer Objects (records)
├── enums/               # UserType e StatusProcess
├── infrastructure/
│   ├── log/             # Slf4jLoggerAdapter (Adapter)
│   └── persistence/     # JpaRepositoryFactory e implementações JPA
├── memento/             # UserMemento e UserMementoCaretaker
├── model/               # Entidades JPA (User, Process, Access)
├── service/             # Serviços de negócio e Template Method
└── view/                # MenuCLIView (interface CLI)
```

## Diagramas

### Diagrama de Casos de Uso - Gerenciar Usuários
![Diagrama de Casos de Uso](diagrams/v3/uc_diagram.jpeg)

### Diagrama de Classes (PENDENTE DE ATUALIZAÇÃO)
![Diagrama de Classes](diagrams/v2/diagrama-de-classes-v2.png)

O código-fonte PlantUML está disponível em [`diagrams/v3/diagrama-de-classes-v3.puml`](diagrams/v3/diagrama-de-classes-v3.puml).

## Padrões de Projeto Utilizados

### 1. Façade

Centraliza o acesso à camada de negócio através de uma única classe controladora.

**Arquivos:**
- [`FacadeSingletonController.java`](src/main/java/com/example/legal_system/controller/FacadeSingletonController.java) — Fachada que recebe as requisições da View e delega para os Services via Commands

```java
@Component
public class FacadeSingletonController {
    private final UserService userService;
    private final ProcessService processService;
    private final AccessReportService accessReportService;
    private final CommandInvoker invoker;

    public void createUser(CreateUserDTO dto) {
        invoker.invoke(new CreateUserCommand(userService, dto));
    }
    // ...
}
```

---

### 2. Command

Cada operação é encapsulada em um objeto Command; o CommandInvoker realiza a execução desacoplada.

**Arquivos:**
- [`Command.java`](src/main/java/com/example/legal_system/controller/command/Command.java) — Interface base
- [`CommandInvoker.java`](src/main/java/com/example/legal_system/controller/command/CommandInvoker.java) — Invocador
- [`CreateUserCommand.java`](src/main/java/com/example/legal_system/controller/command/user/CreateUserCommand.java)
- [`FindAllUsersCommand.java`](src/main/java/com/example/legal_system/controller/command/user/FindAllUsersCommand.java)
- [`FindOneUserCommand.java`](src/main/java/com/example/legal_system/controller/command/user/FindOneUserCommand.java)
- [`UpdateUserCommand.java`](src/main/java/com/example/legal_system/controller/command/user/UpdateUserCommand.java)
- [`UndoUpdateUserCommand.java`](src/main/java/com/example/legal_system/controller/command/user/UndoUpdateUserCommand.java)
- [`RemoveUserCommand.java`](src/main/java/com/example/legal_system/controller/command/user/RemoveUserCommand.java)
- [`CreateProcessCommand.java`](src/main/java/com/example/legal_system/controller/command/process/CreateProcessCommand.java)
- [`CountEntitiesCommand.java`](src/main/java/com/example/legal_system/controller/command/process/CountEntitiesCommand.java)
- [`GenerateAccessReportCommand.java`](src/main/java/com/example/legal_system/controller/command/report/GenerateAccessReportCommand.java)

```java
public interface Command<T> {
    T execute();
}

public class CreateUserCommand implements Command<Void> {
    private final UserService userService;
    private final CreateUserDTO dto;

    @Override
    public Void execute() {
        userService.create(dto);
        return null;
    }
}

public class CommandInvoker {
    public <T> T invoke(Command<T> command) {
        return command.execute();
    }
}
```

---

### 3. Memento

Permite desfazer a última atualização de um usuário. `User` é o Originator, `UserMemento` o snapshot imutável e `UserMementoCaretaker` o guardião do histórico.

**Arquivos:**
- [`User.java`](src/main/java/com/example/legal_system/model/User.java) — Originator (cria e restaura mementos)
- [`UserMemento.java`](src/main/java/com/example/legal_system/memento/UserMemento.java) — Snapshot imutável do estado
- [`UserMementoCaretaker.java`](src/main/java/com/example/legal_system/memento/UserMementoCaretaker.java) — Armazena o último memento por usuário

```java
// Originator — User.java
public UserMemento createMemento() {
    return new UserMemento(id, name, email, type, login, password);
}

public void restoreFromMemento(UserMemento memento) {
    this.name     = memento.getName();
    this.email    = memento.getEmail();
    this.type     = memento.getType();
    this.login    = memento.getLogin();
    this.password = memento.getPassword();
}

// Caretaker — UserMementoCaretaker.java
@Component
public class UserMementoCaretaker {
    private final Map<String, UserMemento> history = new HashMap<>();

    public void save(UserMemento memento) {
        history.put(memento.getId(), memento);
    }

    public Optional<UserMemento> getLast(String userId) {
        return Optional.ofNullable(history.get(userId));
    }

    public void clear(String userId) {
        history.remove(userId);
    }
}
```

---

### 4. Template Method

`ReportGeneratorTemplate` define o esqueleto fixo da geração de relatórios (extrair → processar → formatar → salvar); `HtmlAccessReport` e `PdfAccessReport` implementam os passos variáveis.

**Arquivos:**
- [`ReportGeneratorTemplate.java`](src/main/java/com/example/legal_system/service/ReportGeneratorTemplate.java) — Classe abstrata com o Template Method
- [`HtmlAccessReport.java`](src/main/java/com/example/legal_system/service/HtmlAccessReport.java) — Geração em HTML
- [`PdfAccessReport.java`](src/main/java/com/example/legal_system/service/PdfAccessReport.java) — Geração em PDF

```java
public abstract class ReportGeneratorTemplate {

    public final String generateReport(LocalDate startDate, LocalDate endDate) {
        List<AccessRecordDTO> rawData = extractData(startDate, endDate);     // passo fixo
        AccessStatisticsDTO statistics = processStatistics(rawData);          // passo fixo
        byte[] formattedReport = formatOutput(statistics);                   // passo variável
        return save(formattedReport);                                        // passo variável
    }

    protected abstract byte[] formatOutput(AccessStatisticsDTO statistics);
    protected abstract String save(byte[] formattedReport);
}

@Component("HTML")
public class HtmlAccessReport extends ReportGeneratorTemplate { /* ... */ }

@Component("PDF")
public class PdfAccessReport extends ReportGeneratorTemplate { /* ... */ }
```

---

### 5. Abstract Factory

`RepositoryFactory` abstrai a criação dos repositórios, e `JpaRepositoryFactory` fornece as implementações JPA concretas. O domínio permanece desacoplado da infraestrutura.

**Arquivos:**
- [`RepositoryFactory.java`](src/main/java/com/example/legal_system/domain/RepositoryFactory.java) — Interface da fábrica abstrata
- [`JpaRepositoryFactory.java`](src/main/java/com/example/legal_system/infrastructure/persistence/JpaRepositoryFactory.java) — Implementação concreta JPA

```java
public interface RepositoryFactory {
    IUserRepository getUserRepository();
    IProcessRepository getProcessRepository();
    IAccessRepository getAccessRepository();
}

@Component
public class JpaRepositoryFactory implements RepositoryFactory {
    private final IUserRepository userRepository;
    private final IProcessRepository processRepository;
    private final IAccessRepository accessRepository;

    @Override
    public IUserRepository getUserRepository() { return userRepository; }
    @Override
    public IProcessRepository getProcessRepository() { return processRepository; }
    @Override
    public IAccessRepository getAccessRepository() { return accessRepository; }
}
```

---

### 6. Adapter

`Slf4jLoggerAdapter` adapta o framework SLF4J à interface de domínio `ILogger`, mantendo a camada de negócio desacoplada de qualquer biblioteca de logging.

**Arquivos:**
- [`ILogger.java`](src/main/java/com/example/legal_system/domain/ILogger.java) — Interface de domínio
- [`Slf4jLoggerAdapter.java`](src/main/java/com/example/legal_system/infrastructure/log/Slf4jLoggerAdapter.java) — Adapter para SLF4J

```java
public interface ILogger {
    void info(String message);
    void warn(String message);
    void error(String message, Throwable throwable);
}

@Component
public class Slf4jLoggerAdapter implements ILogger {
    private final Logger logger = LoggerFactory.getLogger(Slf4jLoggerAdapter.class);

    @Override
    public void info(String message) { logger.info(message); }

    @Override
    public void warn(String message) { logger.warn(message); }

    @Override
    public void error(String message, Throwable throwable) { logger.error(message, throwable); }
}
```
