package com.devsu.cuenta.bancaria.data.entities.cuenta;

import com.devsu.cuenta.bancaria.data.entities.core.impl.AbstractEntity;
import com.devsu.cuenta.bancaria.data.enums.TipoMovimientoEnum;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
public class Movimiento extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @NotNull(message = "{movimiento.numeroCuenta.notnull}")
    private Cuenta cuenta;

    @Column(name = "tipo_movimiento")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{movimiento.tipoMovimiento.notnull}")
    private TipoMovimientoEnum tipoMovimiento;

    @Column(name = "saldo_inicial", precision = 10, scale = 2)
    @NotNull(message = "{movimiento.valor.notnull}")
    @Positive(message = "{movimiento.valor.positive}")
    private BigDecimal saldoInicial;

    @Column(name = "movimiento", precision = 10, scale = 2)
    @NotNull(message = "{movimiento.saldo.notnull}")
    @Positive(message = "{movimiento.saldo.positive}")
    private BigDecimal movimiento;

    @Column(name = "saldo_disponible", precision = 10, scale = 2)
    @NotNull(message = "{movimiento.saldo.notnull}")
    @Positive(message = "{movimiento.saldo.positive}")
    private BigDecimal saldoDisponible;

    @Column(name = "fecha")
    @NotNull(message = "{movimiento.fecha.notnull}")
    private LocalDateTime fecha;

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public TipoMovimientoEnum getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimientoEnum tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(BigDecimal movimiento) {
        this.movimiento = movimiento;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
