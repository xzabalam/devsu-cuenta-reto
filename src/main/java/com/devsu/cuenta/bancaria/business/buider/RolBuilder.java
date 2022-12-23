package com.devsu.cuenta.bancaria.business.buider;


import com.devsu.cuenta.bancaria.data.entities.security.Rol;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;

import java.time.LocalDateTime;

public class RolBuilder {

    private static Rol rol;

    public static RolBuilder newBuilder() {
        rol = new Rol();
        return new RolBuilder();
    }

    public Rol build() {
        rol.setFechaCrea(LocalDateTime.now());
        rol.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return rol;
    }


    public RolBuilder nombre(String nombre) {
        rol.setNombre(nombre);
        return this;
    }

    public RolBuilder usuarioCrea(Long idUsuarioCrea) {
        rol.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
