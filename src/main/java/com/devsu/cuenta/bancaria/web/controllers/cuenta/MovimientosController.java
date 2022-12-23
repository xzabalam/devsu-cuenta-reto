package com.devsu.cuenta.bancaria.web.controllers.cuenta;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.business.services.cuenta.ClienteService;
import com.devsu.cuenta.bancaria.business.services.cuenta.CuentaService;
import com.devsu.cuenta.bancaria.business.services.cuenta.LimiteDiarioService;
import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.TipoMovimientoEnum;
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
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;
    private final LimiteDiarioService limiteDiarioService;

    public MovimientosController(ClienteService clienteService, UsuarioService usuarioService, CuentaService cuentaService, LimiteDiarioService limiteDiarioService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
        this.limiteDiarioService = limiteDiarioService;
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

        Movimiento movimiento;
        if (movimientoTo.getTipoMovimiento().equals(TipoMovimientoEnum.DEPOSITO)) {
            movimiento = cuentaService.deposito(numeroCuenta, movimientoTo, usuario.getId());
        } else if (movimientoTo.getTipoMovimiento().equals(TipoMovimientoEnum.RETIRO)) {
            limiteDiarioService.verificarLimiteDiario(cuenta, movimientoTo.getMovimiento(), usuario);
            movimiento = cuentaService.retiro(numeroCuenta, movimientoTo, usuario.getId());
        } else {
            throw new CuentaException("El tipo de movimiento " + movimientoTo.getTipoMovimiento().name() + " no es " +
                    "permitido.");
        }

        return new ResponseEntity<>(MovimientoToEnityMapper.convertirToCuentaTo(movimiento, cuenta, cliente), HttpStatus.OK);
    }
}
