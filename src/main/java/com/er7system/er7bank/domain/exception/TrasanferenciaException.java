package com.er7system.er7bank.domain.exception;

public class TrasanferenciaException extends RuntimeException {

    private static final String MENSAGEM = "Transferencia não realizada com sucesso.";

    public TrasanferenciaException() {
        super(MENSAGEM);
    }

    public TrasanferenciaException(SaldoInsuficienteException e) {
        super(MENSAGEM, e);
    }
}
