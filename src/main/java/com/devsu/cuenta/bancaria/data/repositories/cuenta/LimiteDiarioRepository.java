package com.devsu.cuenta.bancaria.data.repositories.cuenta;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.LimiteDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimiteDiarioRepository extends JpaRepository<LimiteDiario, Long> {

    Optional<LimiteDiario> findByCuenta(Cuenta cuenta);
}
