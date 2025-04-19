package com.er7system.er7bank.domain.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class CartaoDebito extends Cartao {

    private BigDecimal limiteDiario;

    public CartaoDebito() { }

    public CartaoDebito(String nomeTitular, TipoCliente tipoCliente,Conta conta, String senha) {
        super(nomeTitular, tipoCliente, conta, senha);
        this.limiteDiario = BigDecimal.valueOf(900);
    }

    public void setLimiteDiario(BigDecimal limiteDiario) {
        this.limiteDiario = limiteDiario;
    }

    public BigDecimal getLimiteDiario() {
        return limiteDiario;
    }

    public boolean atingeLimiteDiario(BigDecimal valor) {
        return this.getConta().getTransacoes()
            .stream()
            .filter(t -> TipoTransacao.DEBITO.equals(t.getTipo()))
            .map(Transacao::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .add(valor)
            .compareTo(limiteDiario) > 0;
    }
}
