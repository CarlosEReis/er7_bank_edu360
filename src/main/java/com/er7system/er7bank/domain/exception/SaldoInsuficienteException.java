package com.er7system.er7bank.domain.exception;

public class SaldoInsuficienteException extends RuntimeException{

    private static final String MENSAGEM = "Saldo insuficiente para saque";

    public SaldoInsuficienteException() {
        super(MENSAGEM);
    }
}
