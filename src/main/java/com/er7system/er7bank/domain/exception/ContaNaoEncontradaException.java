package com.er7system.er7bank.domain.exception;

public class ContaNaoEncontradaException extends RecursoNaoEncontradoException {

    private static final String CONTA_NAO_ENCONTRADA = "Conta com o ID %s não encontrada";

    public ContaNaoEncontradaException(Integer idCliente) {
        super(String.format(CONTA_NAO_ENCONTRADA, idCliente));
    }

}
