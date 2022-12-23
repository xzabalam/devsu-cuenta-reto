package com.devsu.cuenta.bancaria.web.dtos;

import com.devsu.cuenta.bancaria.data.enums.TipoCuentaEnum;
import com.devsu.cuenta.bancaria.data.enums.TipoMovimientoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MovimientoTo {

    @NotNull(message = "{movimiento.saldo.notnull}")
    @Positive(message = "{movimiento.saldo.positive}")
    private BigDecimal movimiento;

    @NotNull(message = "{movimiento.tipoMovimiento.notnull}")
    private TipoMovimientoEnum tipoMovimiento;


    private LocalDateTime fecha;
    private String nombreCliente;
    private String numeroCuenta;
    private TipoCuentaEnum tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private String estado;

}
