package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
