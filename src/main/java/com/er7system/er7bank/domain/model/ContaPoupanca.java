package com.er7system.er7bank.domain.model;

import com.er7system.er7bank.domain.exception.SaldoInsuficienteException;
import com.er7system.er7bank.domain.exception.TrasanferenciaException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class ContaPoupanca extends Conta {

    @JsonProperty
    private float taxaRendimento;

    public ContaPoupanca() {}

    public ContaPoupanca(Cliente cliente) {
        super(cliente);
        this.setTipoConta(TipoConta.POUPANCA);
        aplicaTaxaBase();
    }

    public void setTaxaRendimento(float taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    public float getTaxaRendimento() {
        return taxaRendimento;
    }

    @Override
    public void sacar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor para saque inválido. Valor deve ser maior que zero.");

        if (valor.compareTo(getSaldo()) > 0)    
            throw new SaldoInsuficienteException();

        saldo = saldo.subtract(valor);
        //registraTransacao(valor, TipoTransacao.SAQUE);
    }

    @Override
    public void depositar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor para depósito inválido. Valor deve ser maior que zero.");
        saldo = saldo.add(valor);
        //registraTransacao(valor, TipoTransacao.DEPOSITO);
    }

    @Override
    public void transferir(BigDecimal valor, Conta conta) {
        try{
            sacar(valor);
            atualizaDataMovimentacao();
            this.registraTransacao(valor, TipoTransacao.SAQUE, DescricaoTransacao.TRANSFERENCIA_ENVIADA);
        } catch (SaldoInsuficienteException e) {
            throw new TrasanferenciaException(e);
        }
        conta.depositar(valor);
        conta.registraTransacao(valor, TipoTransacao.DEPOSITO, DescricaoTransacao.TRANSFERENCIA_RECEBIDA);
    }

    /*
    Conta Poupança
    Taxa de Rendimento Anual:
        0,5% ao ano para clientes Comuns,
        0,7% ao ano para clientes Super,
        0,9% ao ano para clientes Premium */
    @Override
    public void aplicaTaxaBase() {
        switch(getCliente().getTipo()) {
            case COMUM -> taxaRendimento = 0.05f;
            case SUPER -> taxaRendimento = 0.07f;
            case PREMIUM -> taxaRendimento = 0.09f;
        }
    }
}
