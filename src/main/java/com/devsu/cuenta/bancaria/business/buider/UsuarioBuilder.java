package com.devsu.cuenta.bancaria.business.buider;

import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;


public class UsuarioBuilder {

    private static Usuario usuario;

    private static final String getEncode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static final UsuarioBuilder newBuilder() {
        usuario = new Usuario();
        return new UsuarioBuilder();
    }

    public UsuarioBuilder username(String username) {
        this.usuario.setUsername(username);
        final String password = getEncode(username);
        this.usuario.setPassword(password);
        return this;
    }

    public Usuario build() {
        usuario.setFechaCrea(LocalDateTime.now());
        usuario.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return usuario;
    }

    public UsuarioBuilder usernameCrea(Long idUsuarioCrea) {
        this.usuario.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
