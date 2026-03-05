package com.example.legal_system.enums;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum UserType {
    SOCIO_ADMINISTRADOR("Sócio-Administrador"),
    SOCIO("Sócio"),
    ADVOGADO("Advogado"),
    ESTAGIARIO("Estagiário");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UserType fromInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio");
        }

        String normalizedInput = normalize(input);

        return Arrays.stream(values())
                .filter(type -> normalize(type.displayName).equals(normalizedInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tipo inválido. Tipos permitidos: " + allowedValues()));
    }

    public static String allowedValues() {
        return Arrays.stream(values())
                .map(UserType::getDisplayName)
                .collect(Collectors.joining(", "));
    }

    private static String normalize(String value) {
        String withoutAccents = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        return withoutAccents
                .trim()
                .replace('-', ' ')
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }
}