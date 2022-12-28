package com.devsu.cuenta.bancaria.web.controllers.cuenta;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.business.services.cuenta.CuentaService;
import com.devsu.cuenta.bancaria.business.services.movimientos.component.MovimientoComponent;
import com.devsu.cuenta.bancaria.business.services.movimientos.strategy.MovimientoStrategy;
import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;
import com.devsu.cuenta.bancaria.web.mappers.MovimientoToEnityMapper;
import com.devsu.cuenta.bancaria.web.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {
    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;
    private final MovimientoComponent movimientoComponent;

    public MovimientosController(UsuarioService usuarioService, CuentaService cuentaService, MovimientoComponent movimientoComponent) {
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;

        this.movimientoComponent = movimientoComponent;
    }

    @PostMapping("/{numeroCuenta}")
    @Operation(summary = "Permite realizar un movimiento de deposito o de retiro en una cuenta.")
    public ResponseEntity<MovimientoTo> crearMovimiento(@PathVariable("numeroCuenta") String numeroCuenta,
                                                        @Valid @RequestBody MovimientoTo movimientoTo,
                                                        Authentication authentication) {

        RestPreconditions.checkNull(numeroCuenta);
        RestPreconditions.checkNull(movimientoTo);

        Usuario usuario = usuarioService.getUserByUsername(authentication.getName());
        Cuenta cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
        Cliente cliente = cuenta.getCliente();
        MovimientoStrategy movimientoStrategy = movimientoComponent.getEstrategias().get(movimientoTo.getTipoMovimiento());

        if (movimientoStrategy == null) {
            throw new CuentaException("El tipo de movimiento " + movimientoTo.getTipoMovimiento().name() + " no está permitido.");
        }

        Movimiento movimiento = movimientoStrategy.realizarMovimiento(numeroCuenta, movimientoTo, usuario);

        return new ResponseEntity<>(MovimientoToEnityMapper.convertirToCuentaTo(movimiento, cuenta, cliente), HttpStatus.OK);
    }
}
