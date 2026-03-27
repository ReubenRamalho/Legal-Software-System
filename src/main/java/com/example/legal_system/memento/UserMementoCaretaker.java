package com.example.legal_system.memento;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Caretaker do padrão Memento.
 *
 * Responsável por guardar e fornecer o último {@link UserMemento} de cada
 * usuário. Não inspeciona nem modifica o conteúdo dos Mementos — apenas
 * os armazena por ID de usuário.
 *
 * Apenas a atualização mais recente de cada usuário é mantida: ao salvar
 * um novo Memento, o anterior é substituído. Isso implementa o requisito
 * de "desfazer apenas a última atualização da entidade".
 */
@Component
public class UserMementoCaretaker {

    private final Map<String, UserMemento> history = new HashMap<>();

    /**
     * Salva o Memento referente ao estado anterior à última atualização.
     *
     * @param memento snapshot do usuário a ser preservado.
     */
    public void save(UserMemento memento) {
        history.put(memento.getId(), memento);
    }

    /**
     * Recupera o último Memento salvo para o usuário informado.
     *
     * @param userId identificador do usuário.
     * @return um {@link Optional} contendo o Memento, ou vazio se nenhuma
     *         atualização tiver sido registrada para esse usuário.
     */
    public Optional<UserMemento> getLast(String userId) {
        return Optional.ofNullable(history.get(userId));
    }

    /**
     * Remove o Memento do usuário após a operação de desfazer ser concluída,
     * impedindo que o mesmo undo seja aplicado duas vezes.
     *
     * @param userId identificador do usuário.
     */
    public void clear(String userId) {
        history.remove(userId);
    }
}
