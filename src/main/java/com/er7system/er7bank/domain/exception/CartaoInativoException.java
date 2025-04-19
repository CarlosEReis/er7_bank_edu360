package com.er7system.er7bank.domain.exception;

public class CartaoInativoException extends RuntimeException {

    private static final String CARTAO_INATIVO = "O cartão está inativo.";

    public CartaoInativoException() {
        super(CARTAO_INATIVO);
    }
}
