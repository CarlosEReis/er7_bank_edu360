package com.er7system.er7bank.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loja;
    private BigDecimal valor;
    private LocalDate data;

    @ManyToOne
    private Fatura fatura;

    public Pagamento() { }

    public Pagamento(String loja, BigDecimal valor) {
        this.data = LocalDate.now();
        this.loja = loja;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getLoja() {
        return loja;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }
}
