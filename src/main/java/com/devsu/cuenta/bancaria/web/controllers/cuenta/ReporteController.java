package com.devsu.cuenta.bancaria.web.controllers.cuenta;

import com.devsu.cuenta.bancaria.business.services.cuenta.CuentaService;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;
import com.devsu.cuenta.bancaria.web.mappers.MovimientoToEnityMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final CuentaService cuentaService;

    public ReporteController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/movimientos/all/{idCliente}")
    @Operation(summary = "Permite obtener el listado de movimientos de un cliente.")
    public ResponseEntity<List<MovimientoTo>> obtenerTodosLosMovimientos(@PathVariable("idCliente") Long idCliente) {
        List<MovimientoTo> movimientosTo = cuentaService.obtenerMovimientosPorCliente(idCliente).stream().map(movimiento ->
                MovimientoToEnityMapper.convertirToCuentaTo(movimiento, movimiento.getCuenta(),
                        movimiento.getCuenta().getCliente())
        ).collect(Collectors.toList());

        return new ResponseEntity<>(movimientosTo, HttpStatus.OK);
    }

    @GetMapping("/movimientos/{idCliente}")
    @Operation(summary = "Permite obtener el listado de movimientos de un cliente, y se lo puede filtrar por fecha y " +
            "paginarlos resultados, las fechas y la paginación no son campos obligatorios.")
    public ResponseEntity<Page<MovimientoTo>> obtenerTodosLosMovimientosConPaginacion(
            @PathVariable("idCliente") Long idCliente,
            @RequestParam(required = false, value = "fechaDesde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false, value = "fechaHasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "500") int size) {

        Page<MovimientoTo> movimientosTo = cuentaService.obtenerMovimientosPorClienteConPaginacion(idCliente,
                fechaDesde, fechaHasta, page, size).map(movimiento ->
                MovimientoToEnityMapper.convertirToCuentaTo(movimiento, movimiento.getCuenta(),
                        movimiento.getCuenta().getCliente())
        );

        return new ResponseEntity<>(movimientosTo, HttpStatus.OK);
    }
}
