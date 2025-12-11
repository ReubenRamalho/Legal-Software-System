# Legal-Software-System
Projeto da disciplina Métodos de Projeto de Software do Professor Raoni

## Participantes
- Antônio Augusto Dantas Neto - 20230012215
- Deivily Breno Silva Carneiro - 20230012734
- Lucas Gabriel Fontes da Silva - 20230012592
- Rafael de França Silva - 20230012654
- Reuben Lisboa Ramalho Claudino - 20210024602
- Tobias Freire Numeriano - 20230012378

## Diagramas

### Diagrama de Casos de Uso - Gerenciar Usuários
![Diagrama de Casos de Uso](diagrams/v1/diagrama_de_casos_de_uso_gerenciar_usuarios.png)

### Diagrama de Classes
![Diagrama de Classes](diagrams/v1/class_diagram.jpeg)

## Como Executar

### Compilar
```powershell
javac -d code/bin (Get-ChildItem -Path code/src -Include *.java -Recurse).FullName
```

### Executar
```powershell
java -cp code/bin com.usuarios.Main
```
