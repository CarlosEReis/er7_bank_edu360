package com.er7system.er7bank.domain.exception;

public class ClienteNaoEncontradoException extends RecursoNaoEncontradoException {

    private static final String CLIENTE_NAO_ENCONTRADO = "Cliente com o ID %s não encontrado";

    public ClienteNaoEncontradoException(Long idCliente) {
        super(String.format(CLIENTE_NAO_ENCONTRADO, idCliente));
    }
}
