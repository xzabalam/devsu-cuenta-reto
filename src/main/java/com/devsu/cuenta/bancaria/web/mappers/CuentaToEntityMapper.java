package com.devsu.cuenta.bancaria.web.mappers;

import com.devsu.cuenta.bancaria.business.buider.CuentaBuilder;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.CuentaTo;

public final class CuentaToEntityMapper {

    private CuentaToEntityMapper() throws ReflectiveOperationException {
        throw new ReflectiveOperationException("Instances of this type are forbidden.");
    }

    public static final Cuenta convertToCuenta(CuentaTo cuentaTo, Cliente cliente, Usuario usuario) {
        return CuentaBuilder.newBuilder()
                .numeroCuenta(cuentaTo.getNumeroCuenta())
                .tipoCuenta(cuentaTo.getTipoCuenta())
                .saldoInicial(cuentaTo.getSaldoInicial())
                .cliente(cliente)
                .usuarioCrea(usuario.getId())
                .build();
    }

    public static final CuentaTo convertirToCuentaTo(Cuenta cuenta) {
        return CuentaTo.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .tipoCuenta(cuenta.getTipoCuenta())
                .estado(cuenta.getEstado())
                .nombreCliente(cuenta.getCliente().getNombre())
                .build();
    }
}
