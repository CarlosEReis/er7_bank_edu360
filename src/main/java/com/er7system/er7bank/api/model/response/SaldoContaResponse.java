package com.er7system.er7bank.api.model.response;

import com.er7system.er7bank.domain.model.TipoConta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaldoContaResponse(
    Integer conta,
    TipoConta tipo,
    BigDecimal saldo,
    LocalDateTime movimentacao
) {
}
