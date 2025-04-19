package com.er7system.er7bank.domain.exception;

import java.time.YearMonth;

public class CartaoVencidoException extends RuntimeException {

    private static final String CARTAO_VENCIDO = "Não é possível utilizar o cartão, pois ele está vencido (%s).";

    public CartaoVencidoException(YearMonth vencimento) {
        super(String.format(CARTAO_VENCIDO, vencimento));
    }
}
