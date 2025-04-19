package com.er7system.er7bank.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cartao {

    @Transient private static final int ANOS_VIDA_UTIL_CARTAO = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private String nomeTitular;
    private String cvv;

    private BigDecimal saldo = BigDecimal.ZERO;

    @ManyToOne
    private Conta conta;

    private String senha;

    @Enumerated(EnumType.STRING)
    private StatusCartao status = StatusCartao.BLOQUEADO;

    @CreationTimestamp
    private LocalDateTime dataEmissao;

    public Cartao() { }

    public Cartao(String nomeTitular, TipoCliente tipoCliente, Conta conta, String Senha) {
        this.conta = conta;
        this.senha = Senha;
        this.cvv = String.valueOf((int) (Math.random() * 1000));
        this.numero = String.valueOf((long) (Math.random() * 10000000000000000L));
        setNomeTitular(nomeTitular);
    }

    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNomeTitular(String nome) {
        var nomeArray = nome.split(" ");
        var iniciaisDoMeio = getIniciaisNomeDoMeio(nomeArray);
        this.nomeTitular = String.format("%s %s%s",
            nomeArray[0],
            iniciaisDoMeio,
            nomeArray[nomeArray.length-1]).toUpperCase();
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getCvv() {
        return cvv;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public YearMonth getDataValidade() {
        return YearMonth.from(this.dataEmissao.plusYears(ANOS_VIDA_UTIL_CARTAO));
    }

    public boolean estaVencido() {
        return getDataValidade().isBefore(YearMonth.now());
   }

    public void setStatus(StatusCartao status) {
        this.status = status;
    }

    public boolean estaBloqueado() {
        return status.equals(StatusCartao.BLOQUEADO) || dataEmissao.plusYears(ANOS_VIDA_UTIL_CARTAO).isBefore(LocalDateTime.now());
    }

    public StatusCartao getStatus() {
        return status;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    private String getIniciaisNomeDoMeio(String[] array) {
        return Arrays.stream(array, 1, array.length-1)
            .map(n -> n.charAt(0) + " ")
            .reduce("", String::concat);
    }

    public Conta getConta() {
        return conta;
    }
}
