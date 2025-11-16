package com.trabalho.model;

public enum StatusEmprestimo {
    ATIVO("Ativo"),
    DEVOLVIDO("Devolvido");

    private final String descricao;

    StatusEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusEmprestimo statusEnum(String descricao) {
        for (StatusEmprestimo status : values()) {
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
