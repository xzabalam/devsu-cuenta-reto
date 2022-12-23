package com.devsu.cuenta.bancaria.web.dtos;

import com.devsu.cuenta.bancaria.data.enums.TipoCuentaEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CuentaTo {

    @NotNull(message = "{cuenta.numeroCuenta.notnull}")
    @Length(min = 10, max = 10, message = "{cuenta.numeroCuenta.size}")
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{cuenta.tipoCuenta.notnull}")
    private TipoCuentaEnum tipoCuenta;

    @NotNull(message = "{cuenta.saldoInicial.notnull}")
    @Positive(message = "{cuenta.saldoInicial.positive}")
    private BigDecimal saldoInicial;

    private String nombreCliente;
    private String estado;
}
