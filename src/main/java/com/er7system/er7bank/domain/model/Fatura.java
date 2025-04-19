package com.er7system.er7bank.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private CartaoCredito cartao;
    private LocalDate dataVencimento;
    private YearMonth mesReferencia;
    private StatusFatura status = StatusFatura.ABERTO;

    @Transient
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "fatura", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Fatura() { }

    public Fatura(CartaoCredito cartao, YearMonth mesReferencia, LocalDate dataVencimento) {
        this.cartao = cartao;
        this.mesReferencia = mesReferencia;
        this.dataVencimento = dataVencimento;
    }

    public CartaoCredito getCartao() {
        return cartao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public YearMonth getMesReferencia() {
        return mesReferencia;
    }

    public StatusFatura getStatus() {
        return status;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public BigDecimal getTotal() {
        return pagamentos.stream()
            .map(Pagamento::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionaPagamento(Pagamento pagamento) {
        pagamento.setFatura(this);
        pagamentos.add(pagamento);
    }

    public void pagar(BigDecimal valor) {
        if (valor.compareTo(getTotal()) < 0)
            throw new IllegalArgumentException("Valor do pagamento é menor que o total da fatura");

        status = StatusFatura.PAGO;
    }
}
