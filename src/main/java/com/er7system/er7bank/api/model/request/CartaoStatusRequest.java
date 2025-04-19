package com.er7system.er7bank.api.model.request;

import com.er7system.er7bank.domain.model.StatusCartao;

public record CartaoStatusRequest(
    StatusCartao status
) { }
