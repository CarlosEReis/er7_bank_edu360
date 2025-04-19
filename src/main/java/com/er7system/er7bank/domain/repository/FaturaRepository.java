package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.CartaoCredito;
import com.er7system.er7bank.domain.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface FaturaRepository extends JpaRepository<Fatura, Integer> {

    Optional<Fatura> findByCartaoAndMesReferencia(CartaoCredito cartao, YearMonth mesReferencia);
    List<Fatura> findByCartao(CartaoCredito cartao);
    Optional<Fatura> findByIdAndCartaoId(Integer idFatura, Integer idCartao);
}
