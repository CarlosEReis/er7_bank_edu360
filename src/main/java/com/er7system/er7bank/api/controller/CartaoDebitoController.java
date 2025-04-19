package com.er7system.er7bank.api.controller;

import com.er7system.er7bank.api.model.request.AtualizaLimiteResquest;
import com.er7system.er7bank.domain.service.CartaoDebitoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cartoes")
public class CartaoDebitoController {

    private final CartaoDebitoService cartaoDebitoService;

    public CartaoDebitoController(CartaoDebitoService cartaoDebitoService) {
        this.cartaoDebitoService = cartaoDebitoService;
    }

    @PutMapping("/{idCartao}/limite-diario")
    public ResponseEntity<Void> limiteDiario(@PathVariable Integer idCartao, @RequestBody AtualizaLimiteResquest pagamentoRequest) {
        cartaoDebitoService.auterarLimiteDiario(idCartao, pagamentoRequest.valor());
        return ResponseEntity.noContent().build();
    }
}
