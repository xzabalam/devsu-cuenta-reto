package com.devsu.cuenta.bancaria.data.entities.cuenta;

import com.devsu.cuenta.bancaria.data.entities.core.impl.AbstractEntity;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.concurrent.locks.StampedLock;

@Entity
@Table(name = "limite_diario")
public class LimiteDiario extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @NotNull(message = "{cuenta.cliente.notnull}")
    private Cuenta cuenta;

    @Column(name = "limite_diario", precision = 10, scale = 2)
    @NotNull(message = "{cuenta.saldoInicial.notnull}")
    @Positive(message = "{cuenta.saldoInicial.positive}")
    private BigDecimal limiteDiario;

    @Column(name = "valor_tope", precision = 10, scale = 2)
    @NotNull(message = "{cuenta.saldoInicial.notnull}")
    @Positive(message = "{cuenta.saldoInicial.positive}")
    private BigDecimal valorTope;

    @Transient
    private StampedLock lock;

    public LimiteDiario() {
        this.lock = new StampedLock();
    }

    public StampedLock getLock() {
        return lock;
    }

    public void setLock(StampedLock lock) {
        this.lock = lock;
    }

    public BigDecimal getValorTope() {
        return valorTope;
    }

    public void setValorTope(BigDecimal valorTope) {
        this.valorTope = valorTope;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(BigDecimal limiteDiario) {
        this.limiteDiario = limiteDiario;
    }
}
