package com.er7system.er7bank.api.controller;

import com.er7system.er7bank.api.model.request.*;
import com.er7system.er7bank.domain.model.Fatura;
import com.er7system.er7bank.domain.service.CartaoCreditoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cartoes")
public class CartaoCreditoController {

    private final CartaoCreditoService cartaoCreditoService;

    public CartaoCreditoController(CartaoCreditoService cartaoCreditoService) {
        this.cartaoCreditoService = cartaoCreditoService;
    }

    @PutMapping("/{idCartao}/limite")
    public ResponseEntity<Void> limite(@PathVariable Integer idCartao, @RequestBody AtualizaLimiteResquest limite) {
        cartaoCreditoService.alterarLimite(idCartao, limite.valor());
        // TODO: Retornar o valor do novo limite e a data de atualização
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idCartao}/fatura/{idFatura}")
    public ResponseEntity<Fatura> fatura(@PathVariable Integer idCartao, @PathVariable Integer idFatura) {
        var fatura = cartaoCreditoService.buscarFatura(idCartao, idFatura);
        return ResponseEntity.ok(fatura);
    }

    @PostMapping("/{idCartao}/fatura/{idFatura}/pagamento")
    public ResponseEntity<Void> pagamentoFatura(@PathVariable Integer idCartao, @PathVariable Integer idFatura,
                                                @RequestBody PagtoFaturaRequest pagtoFaturaRequest) {
        cartaoCreditoService.pagarFatura(idCartao, idFatura, pagtoFaturaRequest.valor());
        return ResponseEntity.noContent().build();
    }

}
