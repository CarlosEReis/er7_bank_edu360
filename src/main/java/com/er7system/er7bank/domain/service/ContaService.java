package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.dto.DadosPagamentoDTO;
import com.er7system.er7bank.domain.exception.ContaNaoEncontradaException;
import com.er7system.er7bank.domain.exception.TaxaNaoAplicavelException;
import com.er7system.er7bank.domain.exception.TipoDeContaJaCriadaException;
import com.er7system.er7bank.domain.model.*;
import com.er7system.er7bank.domain.repository.ContaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {

    private final ClienteService clienteService;
    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository, ClienteService clienteService) {
        this.clienteService = clienteService;
        this.contaRepository = contaRepository;
    }

    @Transactional
    public Conta criar(Long idCliente, TipoConta tipoConta) {
        Cliente cliente = clienteService.buscar(idCliente);

        validaCriacaoDeContaPorTipoExistente(idCliente, tipoConta);

        Conta conta = null;
        if (TipoConta.CORRENTE.equals(tipoConta))
            conta = new ContaCorrente(cliente);
        if (TipoConta.POUPANCA.equals(tipoConta))
            conta = new ContaPoupanca(cliente);

        conta = contaRepository.save(conta);
        return conta;
    }

    public Conta buscarPorId(Integer idConta) {
        return contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
    }

    @Transactional
    public void transferir(Integer idContaOrigem, Integer idContaDestino, BigDecimal valor) {
        Conta contaOrigem = buscarPorId(idContaOrigem);
        Conta contaDestino = buscarPorId(idContaDestino);
        contaOrigem.transferir(valor, contaDestino);
    }

    @Transactional
    public void depositar(Integer numeroConta, TipoConta tipo, BigDecimal valor) {
        var conta = buscarPorId(numeroConta);
        conta.depositar(valor);
        conta.registraTransacao(valor, TipoTransacao.DEPOSITO, DescricaoTransacao.DEPOSITO_RECEBIDO);
    }

    @Transactional
    public void sacar(Integer numeroConta, TipoConta tipoConta, BigDecimal valor) {
        var conta = buscarPorId(numeroConta);
        conta.sacar(valor);
        conta.registraTransacao(valor, TipoTransacao.SAQUE, DescricaoTransacao.SAQUE_REALIZADO);
    }

    @Transactional
    public void aplicaTaxaManutencao(Integer idConta, BigDecimal valor) {
        Conta conta = buscarPorId(idConta);
        if (conta instanceof ContaCorrente contaCC)
            contaCC.setTaxaManutencao(valor);
        else
            throw new TaxaNaoAplicavelException("Nao de manutenção não pode ser aplicada a uma conta " + conta.getTipoConta());
    }

    @Transactional
    public void aplicaTaxaRendimento(Integer idConta, float valor) {
        Conta conta = buscarPorId(idConta);
        if (conta instanceof ContaPoupanca contaCP)
            contaCP.setTaxaRendimento(valor);
        else
            throw new TaxaNaoAplicavelException("Nao de manutenção não pode ser aplicada a uma conta " + conta.getTipoConta());
    }

    private void validaCriacaoDeContaPorTipoExistente(Long idCliente, TipoConta tipoConta) {
        List<Conta> contas = contaRepository.findByClienteId(idCliente);
        if (!contas.isEmpty()) {
            contas.forEach(conta -> {
                if (tipoConta.equals(conta.getTipoConta()))
                    throw new TipoDeContaJaCriadaException(idCliente, tipoConta);
            });
        }
    }


}
