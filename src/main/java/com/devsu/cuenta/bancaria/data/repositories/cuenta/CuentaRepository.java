package com.devsu.cuenta.bancaria.data.repositories.cuenta;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("select cuenta from Cuenta cuenta join fetch cuenta.cliente cliente where cuenta.numeroCuenta = " +
            ":numeroCuenta and cuenta.estado = :estado")
    Optional<Cuenta> findByNumeroCuentaAndEstado(@Param("numeroCuenta") String numeroCuenta,
                                                 @Param("estado") String estado);

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    Page<Cuenta> findAllByEstado(String estado, Pageable pageable);
}
