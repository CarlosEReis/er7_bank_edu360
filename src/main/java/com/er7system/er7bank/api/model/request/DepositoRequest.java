package com.er7system.er7bank.api.model.request;

import com.er7system.er7bank.domain.model.TipoConta;

import java.math.BigDecimal;

public record DepositoRequest(
        Integer conta,
        TipoConta tipoConta,
        BigDecimal valor
)
{ }
