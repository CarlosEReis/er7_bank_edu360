package com.er7system.er7bank.api.model.request;

import com.er7system.er7bank.domain.model.TipoCartao;

public record CartaoRequest(
    Integer conta,
    TipoCartao tipo,
    int diaVencimento
) { }
