package com.trabalho.model;

public enum StatusFerramenta {
    DISPONIVEL("Disponível"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    StatusFerramenta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusFerramenta statusEnum(String descricao) {
        for (StatusFerramenta status : values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + descricao);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
