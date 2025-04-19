package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.exception.CartaoNaoEncontradoException;
import com.er7system.er7bank.domain.model.Cartao;
import com.er7system.er7bank.domain.model.CartaoCredito;
import com.er7system.er7bank.domain.model.CartaoDebito;
import com.er7system.er7bank.domain.repository.CartaoDebitoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.er7system.er7bank.domain.service.SenhaService.geraSenhaAleatoria;

@Service
public class CartaoDebitoService {

    private final CartaoDebitoRepository cartaoDebitoRepository;
    private ContaService contaService;

    public CartaoDebitoService(CartaoDebitoRepository cartaoDebitoRepository, ContaService contaService) {
        this.cartaoDebitoRepository = cartaoDebitoRepository;
        this.contaService = contaService;
    }

    public CartaoDebito buscarCartaoDebito(Integer idCartao) {
       return cartaoDebitoRepository.findById(idCartao).orElseThrow(CartaoNaoEncontradoException::new);
    }

    @Transactional
    public void auterarLimiteDiario(Integer idCartao, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Valor do limite deve ser maior que zero");
        buscarCartaoDebito(idCartao).setLimiteDiario(valor);
    }

    public Cartao criarCartao(Integer numeroConta) {
        var conta = contaService.buscarPorId(numeroConta);
        return cartaoDebitoRepository
            .save(new CartaoDebito(
                conta.getCliente().getNome(),
                conta.getCliente().getTipo(),
                conta,
                geraSenhaAleatoria()));
    }
}
