package com.er7system.er7bank.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime data;
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Enumerated(EnumType.STRING)
    private DescricaoTransacao descricao;

    @ManyToOne
    private Conta conta;

    public Transacao() {};

    public Transacao(Conta conta, BigDecimal valor, TipoTransacao tipo, DescricaoTransacao descricao) {
        this.conta = conta;
        this.data = LocalDateTime.now();
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public DescricaoTransacao getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", data=" + data +
                ", valor=" + valor +
                ", tipo=" + tipo +
                '}';
    }
}
