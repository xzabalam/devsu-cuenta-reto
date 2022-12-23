package com.devsu.cuenta.bancaria.data.repositories.cuenta;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaAndEstado(Cuenta cuenta, String estado);

    @Query("select m from Movimiento m join fetch m.cuenta cuenta join fetch cuenta.cliente cliente where cliente.id " +
            " = :idCliente and m.estado = 'A' and cuenta.estado = 'A' ")
    List<Movimiento> getAllByCliente(Long idCliente);

    @Query("select m from Movimiento m join m.cuenta cuenta join cuenta.cliente cliente where cliente.id " +
            " = ?1 and m.estado = 'A' and cuenta.estado = 'A'  ")
    Page<Movimiento> getAllByClienteConPaginacion(Long idCliente, Pageable pageable);

    @Query("select m from Movimiento m join m.cuenta cuenta join cuenta.cliente cliente where cliente.id " +
            " = ?1 and m.estado = 'A' and cuenta.estado = 'A' " +
            " and m.fecha between ?2 and ?3 ")
    Page<Movimiento> getAllByClienteConPaginacionFiltroFechas(Long idCliente,
                                                              LocalDateTime fechaDesde,
                                                              LocalDateTime fechaHasta,
                                                              Pageable pageable);
}
