package com.er7system.er7bank.domain.exception;

public class CartaoNaoEncontradoException extends RuntimeException {

    private static final String CARTAO_NAO_ENCONTRADO = "Cartão não encontrado";

    public CartaoNaoEncontradoException() {
        super(CARTAO_NAO_ENCONTRADO);
    }
}
