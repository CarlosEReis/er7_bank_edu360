package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.exception.CartaoInativoException;
import com.er7system.er7bank.domain.exception.CartaoLimiteException;
import com.er7system.er7bank.domain.exception.CartaoNaoEncontradoException;
import com.er7system.er7bank.domain.exception.CartaoVencidoException;
import com.er7system.er7bank.domain.model.*;
import com.er7system.er7bank.domain.repository.CartaoCreditoRepository;
import jakarta.persistence.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.er7system.er7bank.domain.service.SenhaService.geraSenhaAleatoria;

@Service
public class CartaoCreditoService {

    @Transient
    private static final float PERCENTUAL_LIMTE = 0.3f;

    private final ContaService contaService;
    private final CartaoService cartaoService;
    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final FaturaService faturaService;

    public CartaoCreditoService(CartaoService cartaoService, CartaoCreditoRepository cartaoCreditoRepository,
                                ContaService contaService, FaturaService faturaService) {
        this.cartaoService = cartaoService;
        this.cartaoCreditoRepository = cartaoCreditoRepository;
        this.contaService = contaService;
        this.faturaService = faturaService;
    }

    @Transactional
    public Cartao criarCartao(Integer numeroConta, int diaVencimento) {
        var conta = contaService.buscarPorId(numeroConta);
        return cartaoCreditoRepository
            .save(new CartaoCredito(
                conta.getCliente().getNome(),
                conta.getCliente().getTipo(),
                conta,
                geraSenhaAleatoria(),
                setLimiteBase(conta.getCliente().getTipo()),
                diaVencimento)
            );
    }

    public CartaoCredito buscarCartaoCredito(Integer idCartao) {
        return cartaoCreditoRepository.findById(idCartao).orElseThrow(CartaoNaoEncontradoException::new);
    }

    @Transactional
    public void alterarStatus(Integer idCartao, StatusCartao status) {
        Cartao cartao = cartaoService.buscarCartao(idCartao);
        cartao.setStatus(status);
    }

    @Transactional
    public void alterarSenha(Integer idCartao, String senhaAtual, String senhaNova) {
        cartaoService.alterarSenha(idCartao, senhaAtual, senhaNova);
    }

    @Transactional
    public void alterarLimite(Integer idCartao, BigDecimal novoLimite) {
        var cartao = buscarCartaoCredito(idCartao);
        if (cartao.estaBloqueado())
            throw new CartaoInativoException();

        if (cartao.estaVencido())
            throw new CartaoVencidoException(cartao.getDataValidade());

        if (novoLimite.compareTo(cartao.getLimite()) > 0) {
            if (cartao.getAtualizacaoLimite().plusMonths(3).isAfter(LocalDate.now()))
                throw new CartaoLimiteException("Limite só pode ser aumentado a cada 3 meses. Última atualização foi em " + cartao.getAtualizacaoLimite());

            if (isAumentoLimiteValido(novoLimite, cartao.getLimite()))
                throw new CartaoLimiteException("Limite só pode ser aumentado em até 30% do novoLimite atual. Limite atual: " + cartao.getLimite());

            cartao.setLimite(cartao.getLimite().add(novoLimite));
            cartao.setAtualizacaoLimite(LocalDate.now());
        }

        cartao.setLimite(novoLimite);
        cartao.setAtualizacaoLimite(LocalDate.now());
    }

    public Fatura buscarFatura(Integer idCartao, Integer idFatura) {
        return faturaService.buscarFaturaPorId(idCartao, idFatura);
    }

    public void pagarFatura(Integer idCartao, Integer idFatura, BigDecimal valor) {
        faturaService
            .buscarFaturaPorId(idCartao, idFatura)
            .pagar(valor);
    }

    private boolean isAumentoLimiteValido(BigDecimal valor, BigDecimal limiteAtual) {
        return valor.add(limiteAtual).compareTo(limiteAtual.multiply(BigDecimal.valueOf(PERCENTUAL_LIMTE))) < 0;
    }

    private BigDecimal setLimiteBase(TipoCliente tipoCliente) {
        return switch(tipoCliente){
            case COMUM -> BigDecimal.valueOf(1000);
            case SUPER -> BigDecimal.valueOf(5000);
            case PREMIUM -> BigDecimal.valueOf(10000);
        };
    }
}
