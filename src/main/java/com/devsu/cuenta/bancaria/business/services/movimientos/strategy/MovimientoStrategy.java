package com.devsu.cuenta.bancaria.business.services.movimientos.strategy;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;

public interface MovimientoStrategy {
    Movimiento realizarMovimiento(String numeroCuenta, MovimientoTo movimientoTo, Usuario idUsuario);
}
