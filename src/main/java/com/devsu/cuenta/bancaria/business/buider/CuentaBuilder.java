package com.devsu.cuenta.bancaria.business.buider;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.enums.TipoCuentaEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CuentaBuilder {
    private static Cuenta cuenta;

    public static CuentaBuilder newBuilder() {
        cuenta = new Cuenta();
        return new CuentaBuilder();
    }

    public Cuenta build() {
        cuenta.setFechaCrea(LocalDateTime.now());
        cuenta.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return cuenta;
    }

    public CuentaBuilder numeroCuenta(String numeroCuenta) {
        cuenta.setNumeroCuenta(numeroCuenta);
        return this;
    }

    public CuentaBuilder cliente(Cliente cliente) {
        cuenta.setCliente(cliente);
        return this;
    }

    public CuentaBuilder tipoCuenta(TipoCuentaEnum tipoCuenta) {
        cuenta.setTipoCuenta(tipoCuenta);
        return this;
    }

    public CuentaBuilder saldoInicial(BigDecimal saldoInicial) {
        cuenta.setSaldoInicial(saldoInicial);
        return this;
    }

    public CuentaBuilder usuarioCrea(Long idUsuarioCrea) {
        cuenta.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
