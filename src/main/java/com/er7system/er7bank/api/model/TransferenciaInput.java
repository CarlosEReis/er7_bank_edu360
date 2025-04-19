package com.er7system.er7bank.api.model;

import java.math.BigDecimal;

public record TransferenciaInput(
    Integer idContaDestino,
    BigDecimal valor
) { }
