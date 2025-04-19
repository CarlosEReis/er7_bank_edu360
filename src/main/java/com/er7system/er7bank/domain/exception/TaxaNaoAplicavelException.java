package com.er7system.er7bank.domain.exception;

public class TaxaNaoAplicavelException extends RuntimeException {

    public TaxaNaoAplicavelException(String mensagem) {
        super(String.format(mensagem));
    }
}
