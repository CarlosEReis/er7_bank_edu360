package com.er7system.er7bank.domain.exception;

import com.er7system.er7bank.domain.model.TipoConta;

public class TipoDeContaJaCriadaException extends RuntimeException {

    private static final String MENSAGEM = "Cliente com o ID %d já possui uma conta do tipo %s";

    public TipoDeContaJaCriadaException(Long idCliente, TipoConta tipoConta) {
        super(String.format(MENSAGEM, idCliente, tipoConta));
    }
}
