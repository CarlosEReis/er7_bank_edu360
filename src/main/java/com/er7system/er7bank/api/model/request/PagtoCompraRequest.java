package com.er7system.er7bank.api.model.request;

import com.er7system.er7bank.domain.model.TipoTransacao;

import java.math.BigDecimal;

public record PagtoCompraRequest (
        BigDecimal valor,
        String NomeLoja,
        TipoTransacao tipoPagamento,
        String senha
) { }
