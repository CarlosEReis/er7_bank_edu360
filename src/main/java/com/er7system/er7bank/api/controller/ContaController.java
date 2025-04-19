package com.er7system.er7bank.api.controller;

import com.er7system.er7bank.api.model.CriaContaInput;
import com.er7system.er7bank.api.model.TransferenciaInput;
import com.er7system.er7bank.api.model.request.*;
import com.er7system.er7bank.api.model.response.SaldoContaResponse;
import com.er7system.er7bank.domain.dto.DadosPagamentoDTO;
import com.er7system.er7bank.domain.model.Conta;
import com.er7system.er7bank.domain.model.TipoTransacao;
import com.er7system.er7bank.domain.service.ContaService;
import com.er7system.er7bank.domain.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/contas")
public class ContaController {

    /**
     * #### Conta
     * - **POST /contas** - Criar uma nova conta
     * - **GET /contas/{id}** - Obter detalhes de uma conta
     * - **POST /contas/{id}/transferencia** - Realizar uma transferência entre contas
     * - **GET /contas/{id}/saldo** - Consultar saldo da conta
     *
     * // TODO: implementar cadastro de pix para seguir com implementação do endpoint
     * - **POST /contas/{id}/pix** - Realizar um pagamento via Pix
     *
     * - **POST /contas/{id}/deposito** - Realizar um depósito na conta
     * - **POST /contas/{id}/saque** - Realizar um saque da conta
     * - **PUT /contas/{id}/manutencao** - Aplicar taxa de manutenção mensal (para conta corrente)
     * - **PUT /contas/{id}/rendimentos** - Aplicar rendimentos (para conta poupança) */

    private final ContaService contaService;
    private final PagamentoService pagamentoService;

    public ContaController(ContaService contaService, PagamentoService pagamentoService) {
        this.contaService = contaService;
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Conta> criar(@RequestBody CriaContaInput criaContaInput) {
        var conta = contaService.criar(criaContaInput.idCliente(), criaContaInput.tipoConta());
        // todo: implementar o retorno do ID da conta criada
        var uri = "http://localhost:8080/v1/contas/" + conta.getNumero();
        return ResponseEntity.created(URI.create(uri)).body(conta);
    }

    @GetMapping("/{idConta}")
    public Conta buscar(@PathVariable Integer idConta) {
        return contaService.buscarPorId(idConta);
    }

    @PostMapping("/{idConta}/transferencia")
    public ResponseEntity<Void> tranferencia(@PathVariable Integer idConta, @RequestBody TransferenciaInput transferencia) {
        contaService.transferir(idConta, transferencia.idContaDestino(), transferencia.valor());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idConta}/saldo")
    public ResponseEntity<SaldoContaResponse> saldo(@PathVariable Integer idConta) {
        var conta =contaService.buscarPorId(idConta);
        var saldo = new SaldoContaResponse(conta.getNumero(), conta.getTipoConta(), conta.getSaldo(), conta.getUltimaMovimentacao());
        return ResponseEntity.ok(saldo) ;
    }

    @PostMapping("/{idConta}/pix")
    public ResponseEntity<Void> pix(@PathVariable Integer idConta, @RequestBody PagtoCompraRequest pagtoCompraRequest) {
        // A Classe DadosPagamentoDTO pede ID do Cartão, mas na verdade é o ID da conta
        pagamentoService.registraPagamento(new DadosPagamentoDTO(
                idConta,
                pagtoCompraRequest.NomeLoja(),
                pagtoCompraRequest.valor(),
                TipoTransacao.PIX,
                pagtoCompraRequest.senha()));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idConta}/deposito")
    public ResponseEntity<Void> deposito(@PathVariable Integer idConta, @RequestBody DepositoRequest deposito) {
        contaService.depositar(idConta, deposito.tipoConta(), deposito.valor());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idConta}/saque")
    public ResponseEntity<Void> saque(@PathVariable Integer idConta, @RequestBody SaqueRequest saque) {
        contaService.sacar(idConta, saque.tipoConta(), saque.valor());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idConta}/manutencao")
    public ResponseEntity<Void> aplicaTaxaManutencao(@PathVariable Integer idConta, @RequestBody TaxaManutencaoResquest taxaManutencaoResquest) {
        contaService.aplicaTaxaManutencao(idConta, taxaManutencaoResquest.valor());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idConta}/rendimentos")
    public ResponseEntity<Void> aplicaTaxaRedimento(@PathVariable Integer idConta, @RequestBody TaxaRendimentoRequest taxaRendimentoRequest) {
        contaService.aplicaTaxaRendimento(idConta, taxaRendimentoRequest.valor());
        return ResponseEntity.noContent().build();
    }

}
