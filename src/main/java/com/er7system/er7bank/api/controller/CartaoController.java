package com.er7system.er7bank.api.controller;

import com.er7system.er7bank.api.model.request.*;
import com.er7system.er7bank.domain.dto.DadosPagamentoDTO;
import com.er7system.er7bank.domain.model.Cartao;
import com.er7system.er7bank.domain.model.TipoCartao;
import com.er7system.er7bank.domain.service.CartaoCreditoService;
import com.er7system.er7bank.domain.service.CartaoDebitoService;
import com.er7system.er7bank.domain.service.CartaoService;
import com.er7system.er7bank.domain.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;
    private final PagamentoService pagamentoService;
    private final CartaoDebitoService cartaoDebitoService;
    private final CartaoCreditoService cartaoCreditoService;

    public CartaoController(CartaoService cartaoService, PagamentoService pagamentoService, CartaoDebitoService cartaoDebitoService, CartaoCreditoService cartaoCreditoService) {
        this.cartaoService = cartaoService;
        this.pagamentoService = pagamentoService;
        this.cartaoDebitoService = cartaoDebitoService;
        this.cartaoCreditoService = cartaoCreditoService;
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

    @GetMapping("/{idCartao}")
    public ResponseEntity<Cartao> detalhes(@PathVariable Integer idCartao) {
        Cartao cartao = cartaoService.buscarCartao(idCartao);
        return ResponseEntity.ok(cartao);
    }

    @PostMapping("/{idCartao}/pagamento")
    public ResponseEntity<Void> pagamento(@PathVariable Integer idCartao, @RequestBody PagtoCompraRequest pagamentoRequest) {
        pagamentoService.registraPagamento(
            new DadosPagamentoDTO(
                idCartao,
                pagamentoRequest.NomeLoja(),
                pagamentoRequest.valor(),
                pagamentoRequest.tipoPagamento(),
                pagamentoRequest.senha()));
        return ResponseEntity.noContent().build();
    }

    //- **PUT /cartoes/{id}/status** - Ativar ou desativar um cartão
    @PutMapping("/{idCartao}/status")
    public ResponseEntity<Void> status(@PathVariable Integer idCartao, @RequestBody CartaoStatusRequest cartaoStatusRequest) {
        cartaoService.alterarStatus(idCartao, cartaoStatusRequest.status());
        return ResponseEntity.noContent().build();
    }

    //- **PUT /cartoes/{id}/senha** - Alterar senha do cartão
    @PutMapping("/{idCartao}/senha")
    public ResponseEntity<Void> senha(@PathVariable Integer idCartao, @RequestBody AtualizacaoSenhaRequest senhaRequest) {
        cartaoService.alterarSenha(idCartao, senhaRequest.senhaAtual(), senhaRequest.novaSenha());
        return ResponseEntity.noContent().build();
    }

}
