package com.er7system.er7bank.api.controller;

import com.er7system.er7bank.api.model.request.*;
import com.er7system.er7bank.domain.model.Cartao;
import com.er7system.er7bank.domain.model.Fatura;
import com.er7system.er7bank.domain.model.TipoCartao;
import com.er7system.er7bank.domain.repository.CartaoRepository;
import com.er7system.er7bank.domain.service.CartaoCreditoService;
import com.er7system.er7bank.domain.service.CartaoDebitoService;
import com.er7system.er7bank.domain.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/cartoes")
public class CartaoCreditoController {
    private final CartaoDebitoService cartaoDebitoService;


    /*
        - **POST /cartoes** - Emitir um novo cartão
        - **POST /cartoes/{id}/pagamento** - Realizar um pagamento com o cartão
        - **PUT /cartoes/{id}/limite** - Alterar limite do cartão
        - **GET /cartoes/{id}/fatura** - Consultar fatura do cartão de crédito
        - **POST /cartoes/{id}/fatura/pagamento** - Realizar pagamento da fatura do cartão de crédito
    */

    private CartaoCreditoService cartaoCreditoService;

    public CartaoCreditoController(CartaoCreditoService cartaoCreditoService, PagamentoService pagamentoService, CartaoRepository cartaoRepository, CartaoDebitoService cartaoDebitoService) {
        this.cartaoCreditoService = cartaoCreditoService;
        this.cartaoDebitoService = cartaoDebitoService;
    }

    @PostMapping
    public ResponseEntity<Cartao> emitir(@RequestBody CartaoRequest cartaoRequest) {
        // TODO: validar se credito ou debito
        Cartao cartao = null;
        if (TipoCartao.CREDITO.equals(cartaoRequest.tipo()))
            cartao = cartaoCreditoService.criarCartao(cartaoRequest.conta(), cartaoRequest.diaVencimento());

        if (TipoCartao.DEBITO.equals(cartaoRequest.tipo()))
            cartao = cartaoDebitoService.criarCartao(cartaoRequest.conta());

        var uri = "http://localhost:8080/v1/cartoes/" + cartao.getId();
        return ResponseEntity.created(URI.create(uri)).body(cartao);
    }

    @PutMapping("/{idCartao}/limite")
    public ResponseEntity<Void> limite(@PathVariable Integer idCartao, @RequestBody AtualizaLimiteResquest limite) {
        cartaoCreditoService.alterarLimite(idCartao, limite.valor());
        // TODO: Retornar o valor do novo limite e a data de atualização
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idCartao}/fatura/{idFatura}")
    public ResponseEntity<Fatura> faturas(@PathVariable Integer idCartao, @PathVariable Integer idFatura) {
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
