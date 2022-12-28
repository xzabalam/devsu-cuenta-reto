package com.devsu.cuenta.bancaria.business.services.movimientos.component;

import com.devsu.cuenta.bancaria.business.services.movimientos.strategy.MovimientoStrategy;
import com.devsu.cuenta.bancaria.business.services.movimientos.strategy.impl.DepositoStrategy;
import com.devsu.cuenta.bancaria.business.services.movimientos.strategy.impl.RetiroStrategy;
import com.devsu.cuenta.bancaria.data.enums.TipoMovimientoEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovimientoComponent {
    private final DepositoStrategy depositoStrategy;
    private final RetiroStrategy retiroStrategy;

    private Map<TipoMovimientoEnum, MovimientoStrategy> estrategias;

    public MovimientoComponent(DepositoStrategy depositoStrategy, RetiroStrategy retiroStrategy) {
        this.depositoStrategy = depositoStrategy;
        this.retiroStrategy = retiroStrategy;

        estrategias = new HashMap<>();
        estrategias.put(TipoMovimientoEnum.DEPOSITO, this.depositoStrategy);
        estrategias.put(TipoMovimientoEnum.RETIRO, this.retiroStrategy);
    }

    public Map<TipoMovimientoEnum, MovimientoStrategy> getEstrategias() {
        return estrategias;
    }
}
