package com.devsu.cuenta.bancaria.business.services.cuenta;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.CuentaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta crearCuenta(Cuenta cuenta) {
        Optional<Cuenta> optionalCuenta = cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta());

        if (optionalCuenta.isPresent()) {
            throw new CuentaException("La cuenta con el número " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        return cuentaRepository.save(cuenta);
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuentaAndEstado(numeroCuenta, EntityStateEnum.ACTIVO.getEstado())
                .orElseThrow(() -> new CuentaException("No existe la cuenta con número: " + numeroCuenta));
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta obtenerCuentaPorId(Long idCuenta) {
        return cuentaRepository.findById(idCuenta).orElseThrow(() -> new CuentaException("No existe la cuenta con el id: " + idCuenta));
    }

    @Secured("ROLE_ADMINISTRADOR")
    public Page<Cuenta> obtenerCuentas(int page, int size) {
        return cuentaRepository.findAllByEstado(EntityStateEnum.ACTIVO.getEstado(), PageRequest.of(page, size));
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    @Secured("ROLE_ADMINISTRADOR")
    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }


}
