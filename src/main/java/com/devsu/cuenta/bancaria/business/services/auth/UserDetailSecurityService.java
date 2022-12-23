package com.devsu.cuenta.bancaria.business.services.auth;

import com.devsu.cuenta.bancaria.data.entities.security.Permiso;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.repositories.security.PermisoRepository;
import com.devsu.cuenta.bancaria.data.repositories.security.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Se usa para configurara spring security
 */
@Service
public class UserDetailSecurityService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Usuario> optionalUser = usuarioRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            final Usuario usuario = optionalUser.get();
            final List<Permiso> listaPermiso = permisoRepository.findByUsername(usuario.getUsername());
            final String[] roles = listaPermiso.stream().map(permiso -> permiso.getRol().getNombre())
                    .toArray(String[]::new);
            return User.withUsername(usuario.getUsername()).password(usuario.getPassword()).roles(roles).build();
        }

        throw new UsernameNotFoundException(String.format("{security.usuario.no.encontrado}", username));
    }
}
