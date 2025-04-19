package com.er7system.er7bank.domain.model;

import com.er7system.er7bank.domain.exception.SaldoInsuficienteException;
import com.er7system.er7bank.domain.exception.TrasanferenciaException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class ContaCorrente extends Conta {

    @JsonProperty
    private BigDecimal taxaManutencao;

    public ContaCorrente() {}

    public ContaCorrente(Cliente cliente) {
        super(cliente);
        this.setTipoConta(TipoConta.CORRENTE);
        aplicaTaxaBase();
    }

    public void setTaxaManutencao(BigDecimal taxaManutencao) {
        this.taxaManutencao = taxaManutencao;
    }

    public BigDecimal getTaxaManutencao() {
        return taxaManutencao;
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
    Conta Corrente:
    - Taxa de Manutenção Mensal:
        R$ 12,00 para clientes Comuns,
        R$ 8,00 para clientes Super
        R$ 0.00 para clientes Premium. */
    @Override
    public void aplicaTaxaBase() {
        switch(getCliente().getTipo()) {
            case COMUM -> taxaManutencao = BigDecimal.valueOf(12.0);
            case SUPER -> taxaManutencao = BigDecimal.valueOf(8.0);
            case PREMIUM -> taxaManutencao = BigDecimal.valueOf(0);
        }
    }

}
