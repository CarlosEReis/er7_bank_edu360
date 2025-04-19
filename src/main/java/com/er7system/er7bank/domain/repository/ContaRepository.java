package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

    List<Conta> findByClienteId(Long clienteId);

}
