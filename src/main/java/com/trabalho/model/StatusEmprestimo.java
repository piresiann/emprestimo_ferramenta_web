package com.trabalho.model;

/**
 * Enum que representa os possíveis status de um empréstimo.
 * Pode ser ATIVO ou DEVOLVIDO.
 */
public enum StatusEmprestimo {

    /** Empréstimo ainda não devolvido. */
    ATIVO("Ativo"),

    /** Empréstimo devolvido. */
    DEVOLVIDO("Devolvido");

    /** Descrição legível do status. */
    private final String descricao;

    /**
     * Construtor do enum.
     *
     * @param descricao descrição legível do status
     */
    StatusEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição legível do status.
     *
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o enum correspondente à descrição informada.
     *
     * @param descricao descrição legível (ex.: "Ativo", "Devolvido")
     * @return enum correspondente
     * @throws IllegalArgumentException caso a descrição não corresponda a nenhum status
     */
    public static StatusEmprestimo statusEnum(String descricao) {
        for (StatusEmprestimo status : values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + descricao);
    }

    /**
     * Retorna a descrição do status ao converter para String.
     *
     * @return descrição do status
     */
    @Override
    public String toString() {
        return descricao;
    }
}
