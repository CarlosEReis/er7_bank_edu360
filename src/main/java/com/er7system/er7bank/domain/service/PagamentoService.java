package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.dto.DadosPagamentoDTO;
import com.er7system.er7bank.domain.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@Service
public class PagamentoService {

    private final CartaoCreditoService cartaoCreditoService;
    private final FaturaService faturaService;
    private final ContaService contaService;
    private final CartaoDebitoService cartaoDebitoService;

    public PagamentoService(CartaoCreditoService cartaoCreditoService, FaturaService faturaService, ContaService contaService, CartaoDebitoService cartaoDebitoService) {
        this.cartaoCreditoService = cartaoCreditoService;
        this.faturaService = faturaService;
        this.contaService = contaService;
        this.cartaoDebitoService = cartaoDebitoService;
    }

    @Transactional
    public void registraPagamento(DadosPagamentoDTO dadosPagamento) {

        if (TipoTransacao.PIX.equals(dadosPagamento.tipoPagamento())) {
            //                                      mudar, na verdade é ID da CONTA
            Conta conta = contaService.buscarPorId(dadosPagamento.idCartao());
            conta.sacar(dadosPagamento.valor());
            conta.registraTransacao(dadosPagamento.valor(), dadosPagamento.tipoPagamento(), DescricaoTransacao.COMPRA_NO_DEBITO);
        }


        if (TipoTransacao.DEBITO.equals(dadosPagamento.tipoPagamento())) {
            var cartao = cartaoDebitoService.buscarCartaoDebito(dadosPagamento.idCartao());
            if (cartao.estaBloqueado())
                throw new IllegalStateException("Cartao está bloqueado");

            if (cartao.atingeLimiteDiario(dadosPagamento.valor()))
                throw new IllegalStateException("Limite diário atingido");

            Conta conta = contaService.buscarPorId(cartao.getConta().getNumero());
            conta.sacar(dadosPagamento.valor());
            conta.registraTransacao(dadosPagamento.valor(), dadosPagamento.tipoPagamento(), DescricaoTransacao.COMPRA_NO_DEBITO);
        }

        if (TipoTransacao.CREDITO.equals(dadosPagamento.tipoPagamento())) {
            var cartao = cartaoCreditoService.buscarCartaoCredito(dadosPagamento.idCartao());
            if (cartao.estaBloqueado())
                throw new IllegalStateException("Cartao está bloqueado");

            var mes = YearMonth.now();
            var fatura = faturaService.buscarOuGerar(cartao, mes);

            /*if (fatura.estaFechada())
            throw new IllegalStateException(("Fatura já está fechada");*/

            var pagamento = new Pagamento(dadosPagamento.loja(), dadosPagamento.valor());
            fatura.adicionaPagamento(pagamento);
        }


    }
}
