package com.example.legal_system.controller.command;

/**
 * Interface base do padrão Command.
 *
 * Cada operação da camada de negócio será encapsulada como um objeto
 * concreto que implementa esta interface, desacoplando o invocador
 * (Fachada) do receptor (Services).
 *
 * @param <T> Tipo do resultado produzido pela execução do comando.
 *            Usar {@link Void} para comandos sem retorno.
 */
public interface Command<T> {

    /**
     * Executa a operação encapsulada por este comando.
     *
     * @return o resultado da operação, ou {@code null} quando {@code T} é {@link Void}.
     */
    T execute();
}
