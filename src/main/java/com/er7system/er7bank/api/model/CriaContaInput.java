package com.er7system.er7bank.api.model;

import com.er7system.er7bank.domain.model.TipoConta;

public record CriaContaInput(
        Long idCliente,
        TipoConta tipoConta
) {
}
