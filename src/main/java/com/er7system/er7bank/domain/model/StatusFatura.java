package com.er7system.er7bank.domain.model;

public enum StatusFatura {

    ABERTO("Em aberto"),
    PAGO("Pago");

    private final String descricao;

    StatusFatura(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
