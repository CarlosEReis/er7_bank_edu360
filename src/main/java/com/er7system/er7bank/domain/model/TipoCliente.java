package com.er7system.er7bank.domain.model;

public enum TipoCliente {

    COMUM("Comun"),
    SUPER("Super"),
    PREMIUM("Premium");

    private String descricao;

    TipoCliente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
