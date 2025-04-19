package com.er7system.er7bank.domain.dto;

import com.er7system.er7bank.domain.model.TipoTransacao;

import java.math.BigDecimal;

public record DadosPagamentoDTO (
        Integer idCartao,
        String loja,
        BigDecimal valor,
        TipoTransacao tipoPagamento,
        String senha
) { }
