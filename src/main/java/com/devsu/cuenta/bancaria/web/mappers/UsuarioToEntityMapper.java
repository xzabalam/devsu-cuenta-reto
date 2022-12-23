package com.devsu.cuenta.bancaria.web.mappers;

import com.devsu.cuenta.bancaria.business.buider.UsuarioBuilder;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.UsuarioTo;

public final class UsuarioToEntityMapper {

    private UsuarioToEntityMapper() throws ReflectiveOperationException {
        throw new ReflectiveOperationException("Instances of this type are forbidden.");
    }

    public static final Usuario convertToUsuario(UsuarioTo usuarioTo, Long idUsuarioCrea) {
        return UsuarioBuilder.newBuilder()
                .username(usuarioTo.getUsername())
                .usernameCrea(idUsuarioCrea)
                .build();
    }
}
