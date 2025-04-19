package com.er7system.er7bank.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;
    protected BigDecimal saldo = BigDecimal.ZERO;
    private LocalDateTime ultimaMovimentacao = LocalDateTime.now();

    @ManyToOne
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    @OrderBy("id DESC")
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private List<Transacao> transacoes;

    //private List<String> chavesPix;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    public Conta(){}

    public Conta(Cliente cliente) {
        this.cliente = cliente;
        this.transacoes = new ArrayList<>();
        //this.chavesPix = new ArrayList<>();
    }

    public abstract void sacar(BigDecimal valor);
    public abstract void depositar(BigDecimal valor);
    public abstract void transferir(BigDecimal valor, Conta contaDestino);
    public abstract void aplicaTaxaBase();

    public void setNumero(int i) {
        this.numero = i;
    }

    public Integer getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getUltimaMovimentacao() {
        return ultimaMovimentacao;
    }

    public void atualizaDataMovimentacao() {
        ultimaMovimentacao = LocalDateTime.now();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    /*public List<String> getChavesPix() {
        return chavesPix;
    }*/

    public void pix(BigDecimal valor, String chave) {}

    public void registraTransacao(BigDecimal valor, TipoTransacao tipoTransacao, DescricaoTransacao descricao) {
        var transacao = new Transacao(this, valor, tipoTransacao, descricao);
        this.transacoes.add(transacao);
        this.atualizaDataMovimentacao();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void debitar(BigDecimal valor) {
        sacar(valor);
    }
}
