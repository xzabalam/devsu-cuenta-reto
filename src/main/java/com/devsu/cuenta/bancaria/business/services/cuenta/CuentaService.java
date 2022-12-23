package com.devsu.cuenta.bancaria.business.services.cuenta;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.business.exceptions.SaldoInsuficienteException;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.enums.TipoMovimientoEnum;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.CuentaRepository;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.LimiteDiarioRepository;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.MovimientoRepository;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final LimiteDiarioRepository limiteDiarioRepository;

    public CuentaService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository, LimiteDiarioRepository limiteDiarioRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.limiteDiarioRepository = limiteDiarioRepository;
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta crearCuenta(Cuenta cuenta) {
        Optional<Cuenta> optionalCuenta = cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta());

        if (optionalCuenta.isPresent()) {
            throw new CuentaException("La cuenta con el número " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        return cuentaRepository.save(cuenta);
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuentaAndEstado(numeroCuenta, EntityStateEnum.ACTIVO.getEstado())
                .orElseThrow(() -> new CuentaException("No existe la cuenta con número: " + numeroCuenta));
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta obtenerCuentaPorId(Long idCuenta) {
        return cuentaRepository.findById(idCuenta).orElseThrow(() -> new CuentaException("No existe la cuenta con el id: " + idCuenta));
    }

    @Secured("ROLE_ADMINISTRADOR")
    public Page<Cuenta> obtenerCuentas(int page, int size) {
        return cuentaRepository.findAllByEstado(EntityStateEnum.ACTIVO.getEstado(), PageRequest.of(page, size));
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    @Secured("ROLE_ADMINISTRADOR")
    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
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
    public Movimiento deposito(String numeroCuenta, MovimientoTo movimiento, Long idUsuario) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuentaAndEstado(numeroCuenta,
                EntityStateEnum.ACTIVO.getEstado()).orElseThrow(() -> new CuentaException("No existe la cuenta con número: " + numeroCuenta));


        BigDecimal saldoActual = cuenta.getSaldoInicial();
        movimiento.setSaldoInicial(cuenta.getSaldoInicial());

        long stamp = cuenta.getLock().writeLock();

        try {
            cuenta.setSaldoInicial(saldoActual.add(movimiento.getMovimiento()));
            movimiento.setSaldoDisponible(saldoActual.add(movimiento.getMovimiento()));
            cuenta.setFechaModifica(LocalDateTime.now());
            cuenta.setUsuarioModifica(idUsuario);
            cuentaRepository.save(cuenta);

            // Crear movimiento
            return crearMovimiento(movimiento, idUsuario, cuenta, TipoMovimientoEnum.DEPOSITO);
        } finally {
            cuenta.getLock().unlockWrite(stamp);
        }
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Movimiento retiro(String numeroCuenta, MovimientoTo movimiento, Long idUsuario) throws SaldoInsuficienteException {
        Cuenta cuenta = cuentaRepository.findByNumeroCuentaAndEstado(numeroCuenta,
                EntityStateEnum.ACTIVO.getEstado()).orElseThrow(() -> new CuentaException("No existe la cuenta con número: " + numeroCuenta));

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        movimiento.setSaldoInicial(cuenta.getSaldoInicial());

        long stamp = cuenta.getLock().writeLock();

        try {
            if (movimiento.getMovimiento().compareTo(saldoActual) > 0) {
                throw new SaldoInsuficienteException("Saldo no disponible.");
            }

            cuenta.setSaldoInicial(saldoActual.subtract(movimiento.getMovimiento()));
            movimiento.setSaldoDisponible(saldoActual.subtract(movimiento.getMovimiento()));
            cuenta.setFechaModifica(LocalDateTime.now());
            cuenta.setUsuarioModifica(idUsuario);
            cuentaRepository.save(cuenta);

            // Crear movimiento
            return crearMovimiento(movimiento, idUsuario, cuenta, TipoMovimientoEnum.RETIRO);
        } finally {
            cuenta.getLock().unlockWrite(stamp);
        }
    }

    private Movimiento crearMovimiento(MovimientoTo movimientoTo, Long idUsuario, Cuenta cuenta,
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
