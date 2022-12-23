package com.devsu.cuenta.bancaria.data.entities.cuenta;

import com.devsu.cuenta.bancaria.data.entities.core.impl.AbstractEntity;
import com.devsu.cuenta.bancaria.data.enums.TipoCuentaEnum;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.concurrent.locks.StampedLock;

@Entity
@Table(name = "cuenta")
public class Cuenta extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @NotNull(message = "{cuenta.cliente.notnull}")
    private Cliente cliente;

    @Column(name = "numero_cuenta")
    @NotNull(message = "{cuenta.numeroCuenta.notnull}")
    @Length(min = 10, max = 10, message = "{cuenta.numeroCuenta.size}")
    private String numeroCuenta;

    @Column(name = "tipo_cuenta")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{cuenta.tipoCuenta.notnull}")
    private TipoCuentaEnum tipoCuenta;

    @Column(name = "saldo_inicial", precision = 10, scale = 2)
    @NotNull(message = "{cuenta.saldoInicial.notnull}")
    @Positive(message = "{cuenta.saldoInicial.positive}")
    private BigDecimal saldoInicial;

    @Transient
    private StampedLock lock;

    public Cuenta() {
        this.lock = new StampedLock();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public StampedLock getLock() {
        return lock;
    }

    public void setLock(StampedLock lock) {
        this.lock = lock;
    }
}
