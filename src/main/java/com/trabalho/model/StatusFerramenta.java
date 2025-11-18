package com.trabalho.model;

/**
 * Enum que representa o status de disponibilidade de uma ferramenta.
 * Pode ser DISPONIVEL ou INDISPONIVEL.
 */
public enum StatusFerramenta {

    /** Ferramenta disponível para empréstimo. */
    DISPONIVEL("Disponível"),

    /** Ferramenta indisponível para empréstimo. */
    INDISPONIVEL("Indisponível");

    /** Descrição legível do status. */
    private final String descricao;

    /**
     * Construtor do enum.
     *
     * @param descricao descrição legível para o status
     */
    StatusFerramenta(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição do status.
     *
     * @return descrição legível do status
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte uma descrição em seu enum correspondente.
     *
     * @param descricao texto legível (ex.: "Disponível", "Indisponível")
     * @return enum correspondente
     * @throws IllegalArgumentException se a descrição não corresponder a nenhum status
     */
    public static StatusFerramenta statusEnum(String descricao) {
        for (StatusFerramenta status : values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + descricao);
    }

    /**
     * Retorna a descrição quando convertido para String.
     *
     * @return descrição do status
     */
    @Override
    public String toString() {
        return descricao;
    }
}
