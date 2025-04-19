package com.er7system.er7bank.domain.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class CartaoCredito extends Cartao {

    private BigDecimal limite;
    private LocalDate atualizacaoLimite;
    private int diaVencimento;

    public CartaoCredito() { }

    public CartaoCredito(String nomeTitular, TipoCliente tipoCliente, Conta conta, String senha, BigDecimal limiteBase, int diaVencimento) {
        super(nomeTitular, tipoCliente, conta, senha);
        setLimiteBase(limiteBase);
        this.diaVencimento = diaVencimento;
    }

    private void setLimiteBase(BigDecimal limite) {
        this.limite = limite;
        this.atualizacaoLimite = LocalDate.now();
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setAtualizacaoLimite(LocalDate atualizacaoLimite) {
        this.atualizacaoLimite = atualizacaoLimite;
    }

    public LocalDate getAtualizacaoLimite() {
        return atualizacaoLimite;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }
}
