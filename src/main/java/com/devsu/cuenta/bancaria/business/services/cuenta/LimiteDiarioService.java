package com.devsu.cuenta.bancaria.business.services.cuenta;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.cuenta.LimiteDiario;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.LimiteDiarioRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LimiteDiarioService {
    private static final String LIMITE_EXCEDIDO = "Cupo diario excedido.";
    private final LimiteDiarioRepository limiteDiarioRepository;

    public LimiteDiarioService(LimiteDiarioRepository limiteDiarioRepository) {
        this.limiteDiarioRepository = limiteDiarioRepository;
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public void verificarLimiteDiario(Cuenta cuenta, BigDecimal montoRetirar, Usuario usuario) {
        Optional<LimiteDiario> limiteDiario = limiteDiarioRepository.findByCuenta(cuenta);

        if (limiteDiario.isEmpty()) {
            crearLimite(cuenta, montoRetirar, usuario);
        } else {
            actualizarLimite(montoRetirar, usuario, limiteDiario.get());
        }
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public void eliminarTodosLosRegistros() {
        limiteDiarioRepository.deleteAll();
    }

    private void actualizarLimite(BigDecimal montoRetirar, Usuario usuario, LimiteDiario limite) {
        if (montoRetirar.add(limite.getLimiteDiario()).compareTo(limite.getValorTope()) == 1) {
            throw new CuentaException(LIMITE_EXCEDIDO);
        }

        long stamp = limite.getLock().writeLock();

        try {
            limite.setLimiteDiario(montoRetirar.add(limite.getLimiteDiario()));
            limite.setFechaModifica(LocalDateTime.now());
            limite.setUsuarioModifica(usuario.getId());

            limiteDiarioRepository.save(limite);
        } finally {
            limite.getLock().unlockWrite(stamp);
        }
    }

    private void crearLimite(Cuenta cuenta, BigDecimal montoRetirar, Usuario usuario) {
        if (montoRetirar.compareTo(BigDecimal.valueOf(1000)) == 1) {
            throw new CuentaException(LIMITE_EXCEDIDO);
        }

        // se debe crear el limite diario con 1000 como valor tope
        LimiteDiario limite = new LimiteDiario();
        long stamp = limite.getLock().writeLock();

        try {
            limite.setCuenta(cuenta);
            limite.setValorTope(BigDecimal.valueOf(1000));
            limite.setLimiteDiario(montoRetirar);
            limite.setEstado(EntityStateEnum.ACTIVO.getEstado());
            limite.setUsuarioCrea(usuario.getId());
            limite.setFechaCrea(LocalDateTime.now());
            limite.setFechaModifica(LocalDateTime.now());
            limite.setUsuarioModifica(usuario.getId());

            limiteDiarioRepository.save(limite);
        } finally {
            limite.getLock().unlockWrite(stamp);
        }
    }
}
