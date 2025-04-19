package com.er7system.er7bank.domain.exception;

public abstract class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
