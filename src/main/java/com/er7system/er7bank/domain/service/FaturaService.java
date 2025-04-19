package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.model.CartaoCredito;
import com.er7system.er7bank.domain.model.Fatura;
import com.er7system.er7bank.domain.repository.FaturaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class FaturaService {

    private final FaturaRepository faturaRepository;

    public FaturaService(FaturaRepository faturaRepository) {
        this.faturaRepository = faturaRepository;
    }

    public List<Fatura> listarFaturas(CartaoCredito cartao) {
        return faturaRepository.findByCartao(cartao);
    }

    public Fatura buscarFaturaPorId(Integer idCartao, Integer idFatura) {
        return faturaRepository.findByIdAndCartaoId(idFatura, idCartao)
                .orElseThrow(() -> new IllegalStateException("Fatura não encontrada"));
    }

    public Fatura buscarFaturaAtual(CartaoCredito cartao) {
        var mesAtual = YearMonth.now();
        return faturaRepository.findByCartaoAndMesReferencia(cartao, mesAtual)
                .orElseThrow(() -> new IllegalStateException("Fatura não encontrada"));
    }

    public Fatura buscarOuGerar(CartaoCredito cartao, YearMonth mes) {
        return faturaRepository.findByCartaoAndMesReferencia(cartao, mes)
                .orElseGet(() -> gerarFatura(cartao, mes));
    }

    private Fatura gerarFatura(CartaoCredito cartao, YearMonth mes) {
        var dataVencimento = calculaDataVencimento(cartao.getDiaVencimento(), mes);
        var novaFatura = new Fatura(cartao, mes, dataVencimento);
        return faturaRepository.save(novaFatura);
    }

    private LocalDate calculaDataVencimento(int diaVencimento, YearMonth mes) {
        var proximoMes = mes.plusMonths(1);
        int dia = Math.min(diaVencimento, proximoMes.lengthOfMonth());
        return proximoMes.atDay(dia);
    }
}
