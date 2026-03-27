package com.example.legal_system.controller.command;

/**
 * Invoker do padrão Command.
 *
 * Responsável por receber um objeto {@link Command} e disparar sua execução.
 * Desacopla completamente o remetente da requisição (a Fachada) do receptor
 * que de fato executa a lógica de negócio (os Services).
 *
 * A Fachada não precisa saber como o Command foi construído nem como ele
 * funciona internamente — ela apenas entrega o Command ao Invoker e obtém
 * o resultado de volta.
 */
public class CommandInvoker {

    /**
     * Executa o Command fornecido e retorna seu resultado.
     *
     * @param <T>     Tipo do resultado produzido pelo Command.
     * @param command O Command a ser executado.
     * @return O resultado retornado pelo Command.
     */
    public <T> T invoke(Command<T> command) {
        return command.execute();
    }
}
