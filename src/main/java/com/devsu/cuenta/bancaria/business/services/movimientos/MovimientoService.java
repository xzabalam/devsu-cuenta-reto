package com.devsu.cuenta.bancaria.business.services.movimientos;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.enums.TipoMovimientoEnum;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.MovimientoRepository;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;

    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Movimiento crearMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public List<Movimiento> obtenerMovimientosPorCuenta(Cuenta cuenta) {
        return movimientoRepository.findByCuentaAndEstado(cuenta, EntityStateEnum.ACTIVO.getEstado());
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public List<Movimiento> obtenerMovimientosPorCliente(Long idCliente) {
        return movimientoRepository.getAllByCliente(idCliente);
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Page<Movimiento> obtenerMovimientosPorClienteConPaginacion(Long idCliente, LocalDateTime fechaDesde,
                                                                      LocalDateTime fechaHasta, int page, int size) {

        if (!Objects.isNull(fechaDesde) && !Objects.isNull(fechaHasta)) {
            return movimientoRepository.getAllByClienteConPaginacionFiltroFechas(idCliente, fechaDesde, fechaHasta,
                    PageRequest.of(page, size));
        }

        if (!Objects.isNull(fechaDesde) && Objects.isNull(fechaHasta)) {
            return movimientoRepository.getAllByClienteConPaginacionFiltroFechas(idCliente, fechaDesde, LocalDateTime.now(),
                    PageRequest.of(page, size));
        }

        return movimientoRepository.getAllByClienteConPaginacion(idCliente, PageRequest.of(page, size));
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Movimiento crearMovimiento(MovimientoTo movimientoTo, Long idUsuario, Cuenta cuenta,
                                      TipoMovimientoEnum tipoMovimiento) {
        Movimiento movimiento = new Movimiento();

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento(tipoMovimiento);

        movimiento.setSaldoInicial(movimientoTo.getSaldoInicial());
        movimiento.setSaldoDisponible(movimientoTo.getSaldoDisponible());
        movimiento.setMovimiento(movimientoTo.getMovimiento());

        movimiento.setUsuarioCrea(idUsuario);
        movimiento.setFechaCrea(LocalDateTime.now());
        movimiento.setEstado(EntityStateEnum.ACTIVO.getEstado());

        return movimientoRepository.save(movimiento);
    }
}
