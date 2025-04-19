package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.exception.CartaoNaoEncontradoException;
import com.er7system.er7bank.domain.model.*;
import com.er7system.er7bank.domain.repository.CartaoDebitoRepository;
import com.er7system.er7bank.domain.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final SenhaService senhaService;

    public CartaoService(CartaoRepository cartaoRepository, CartaoDebitoRepository cartaoDebitoRepository, SenhaService senhaService) {
        this.cartaoRepository = cartaoRepository;
        this.senhaService = senhaService;
    }

    public Cartao buscarCartao(Integer idCartao) {
        return cartaoRepository.findById(idCartao).orElseThrow(CartaoNaoEncontradoException::new);
    }

    @Transactional
    public void alterarStatus(Integer idCartao, StatusCartao status) {
        Cartao cartao = buscarCartao(idCartao);
        cartao.setStatus(status);
    }

    @Transactional
    public void alterarSenha(Integer idCartao, String senhaAtual, String senhaNova) {
        Cartao cartao = buscarCartao(idCartao);
        senhaService.alterarSenha(cartao, senhaAtual, senhaNova);
    }

}
