package com.er7system.er7bank.api.controller;

import com.er7system.er7bank.api.model.request.*;
import com.er7system.er7bank.domain.dto.DadosPagamentoDTO;
import com.er7system.er7bank.domain.model.Cartao;
import com.er7system.er7bank.domain.service.CartaoService;
import com.er7system.er7bank.domain.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cartoes")
public class CartaoController {

    /*
    - **PUT /cartoes/{id}/status** - Ativar ou desativar um cartão
    - **PUT /cartoes/{id}/senha** - Alterar senha do cartão
    - **GET /cartoes/{id}** - Obter detalhes de um cartão */

    private final CartaoService cartaoService;
    private final PagamentoService pagamentoService;

    public CartaoController(CartaoService cartaoService, PagamentoService pagamentoService) {
        this.cartaoService = cartaoService;
        this.pagamentoService = pagamentoService;
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

}
