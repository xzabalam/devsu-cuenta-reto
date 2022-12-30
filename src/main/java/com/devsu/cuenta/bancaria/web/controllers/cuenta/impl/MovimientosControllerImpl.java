package com.devsu.cuenta.bancaria.web.controllers.cuenta.impl;

import com.devsu.cuenta.bancaria.business.services.cuenta.CuentaService;
import com.devsu.cuenta.bancaria.business.services.movimientos.component.MovimientoComponent;
import com.devsu.cuenta.bancaria.business.services.movimientos.strategy.MovimientoStrategy;
import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.controllers.cuenta.MovimientosController;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;
import com.devsu.cuenta.bancaria.web.mappers.MovimientoToEnityMapper;
import com.devsu.cuenta.bancaria.web.util.RestPreconditions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MovimientosControllerImpl implements MovimientosController {
    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;
    private final MovimientoComponent movimientoComponent;

    public MovimientosControllerImpl(UsuarioService usuarioService, CuentaService cuentaService, MovimientoComponent movimientoComponent) {
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
        this.movimientoComponent = movimientoComponent;
    }

    @Override
    public ResponseEntity<MovimientoTo> crearMovimiento(String numeroCuenta, @Valid MovimientoTo movimientoTo,
                                                        Authentication authentication) {
        RestPreconditions.checkNull(numeroCuenta);
        RestPreconditions.checkNull(movimientoTo);

        Usuario usuario = usuarioService.getUserByUsername(authentication.getName());
        Cuenta cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
        Cliente cliente = cuenta.getCliente();
        MovimientoStrategy movimientoStrategy = movimientoComponent.getEstrategia(movimientoTo.getTipoMovimiento());
        Movimiento movimiento = movimientoStrategy.realizarMovimiento(numeroCuenta, movimientoTo, usuario);

        return new ResponseEntity<>(MovimientoToEnityMapper.convertirToCuentaTo(movimiento, cuenta, cliente), HttpStatus.OK);
    }
}
