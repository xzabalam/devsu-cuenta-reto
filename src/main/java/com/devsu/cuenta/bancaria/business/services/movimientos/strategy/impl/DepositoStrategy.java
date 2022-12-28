package com.devsu.cuenta.bancaria.business.services.movimientos.strategy.impl;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.business.services.movimientos.MovimientoService;
import com.devsu.cuenta.bancaria.business.services.movimientos.strategy.MovimientoStrategy;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.enums.TipoMovimientoEnum;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.CuentaRepository;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DepositoStrategy implements MovimientoStrategy {

    private final MovimientoService movimientoService;
    private final CuentaRepository cuentaRepository;

    public DepositoStrategy(MovimientoService movimientoService, CuentaRepository cuentaRepository) {
        this.movimientoService = movimientoService;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Movimiento realizarMovimiento(String numeroCuenta, MovimientoTo movimientoTo, Usuario usuario) {
        return deposito(numeroCuenta, movimientoTo, usuario.getId());
    }

    private Movimiento deposito(String numeroCuenta, MovimientoTo movimiento, Long idUsuario) {
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
            return movimientoService.crearMovimiento(movimiento, idUsuario, cuenta, TipoMovimientoEnum.DEPOSITO);
        } finally {
            cuenta.getLock().unlockWrite(stamp);
        }
    }
}
