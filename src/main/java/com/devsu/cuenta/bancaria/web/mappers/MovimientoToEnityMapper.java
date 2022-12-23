package com.devsu.cuenta.bancaria.web.mappers;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Movimiento;
import com.devsu.cuenta.bancaria.web.dtos.MovimientoTo;

public final class MovimientoToEnityMapper {

    private MovimientoToEnityMapper() throws ReflectiveOperationException {
        throw new ReflectiveOperationException("Instances of this type are forbidden.");
    }

    public static final MovimientoTo convertirToCuentaTo(Movimiento movimiento, Cuenta cuenta, Cliente cliente) {
        return MovimientoTo.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .nombreCliente(cliente.getNombre())
                .saldoInicial(movimiento.getSaldoInicial())
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .estado(movimiento.getEstado())
                .movimiento(movimiento.getMovimiento())
                .saldoDisponible(movimiento.getSaldoDisponible())
                .build();
    }
}
