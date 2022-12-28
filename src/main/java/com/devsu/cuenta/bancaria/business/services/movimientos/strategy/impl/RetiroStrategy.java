package com.devsu.cuenta.bancaria.business.services.movimientos.strategy.impl;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.business.exceptions.SaldoInsuficienteException;
import com.devsu.cuenta.bancaria.business.services.cuenta.LimiteDiarioService;
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
public class RetiroStrategy implements MovimientoStrategy {
    private final MovimientoService movimientoService;
    private final LimiteDiarioService limiteDiarioService;
    private final CuentaRepository cuentaRepository;

    public RetiroStrategy(MovimientoService movimientoService, LimiteDiarioService limiteDiarioService, CuentaRepository cuentaRepository) {
        this.movimientoService = movimientoService;
        this.limiteDiarioService = limiteDiarioService;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Movimiento realizarMovimiento(String numeroCuenta, MovimientoTo movimientoTo, Usuario usuario) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuentaAndEstado(numeroCuenta,
                EntityStateEnum.ACTIVO.getEstado()).orElseThrow(() -> new CuentaException("No existe la cuenta con número: " + numeroCuenta));

        limiteDiarioService.verificarLimiteDiario(cuenta, movimientoTo.getMovimiento(), usuario);
        return retiro(cuenta, movimientoTo, usuario.getId());
    }

    private Movimiento retiro(Cuenta cuenta, MovimientoTo movimiento, Long idUsuario) throws SaldoInsuficienteException {
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
            return movimientoService.crearMovimiento(movimiento, idUsuario, cuenta, TipoMovimientoEnum.RETIRO);
        } finally {
            cuenta.getLock().unlockWrite(stamp);
        }
    }
}
