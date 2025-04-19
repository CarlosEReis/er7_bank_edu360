package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
}
