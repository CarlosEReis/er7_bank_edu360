package com.er7system.er7bank.domain.dto;

import com.er7system.er7bank.domain.model.TipoConta;

import java.math.BigDecimal;

public record DepositoDTO(
        Integer contaOrigem,
        BigDecimal valor,

        String titularDestinatario,
        String cpfDestinatario,
        Integer contaDestinatario,
        TipoConta tipoContaDestinatario
) { }
