package com.devsu.cuenta.bancaria.data.repositories.cuenta;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByIdentificacionAndEstado(String identificacion, String estado);

    Optional<Cliente> findByIdentificacion(String identificacion);

    Page<Cliente> findAllByEstado(String estado, Pageable pageable);

}
