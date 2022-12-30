package com.devsu.cuenta.bancaria.web.controllers.cuenta;

import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/movimientos")
public interface MovimientosController {

    @PostMapping("/{numeroCuenta}")
    @Operation(summary = "Permite realizar un movimiento de deposito o de retiro en una cuenta.")
    ResponseEntity<MovimientoTo> crearMovimiento(@PathVariable("numeroCuenta") String numeroCuenta,
                                                 @Validated @RequestBody MovimientoTo movimientoTo,
                                                 Authentication authentication);
}
