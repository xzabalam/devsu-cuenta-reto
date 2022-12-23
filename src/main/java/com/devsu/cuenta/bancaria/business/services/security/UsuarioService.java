package com.devsu.cuenta.bancaria.business.services.security;

import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.repositories.security.PermisoRepository;
import com.devsu.cuenta.bancaria.data.repositories.security.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PermisoRepository permisoRepository;
    private final MessageSource messageSource;

    public UsuarioService(UsuarioRepository usuarioRepository, PermisoRepository permisoRepository,
                          MessageSource messageSource) {
        this.usuarioRepository = usuarioRepository;
        this.permisoRepository = permisoRepository;
        this.messageSource = messageSource;
    }

    @Transactional
    @Secured("ROLE_ADMINISTRADOR")
    public Usuario createUser(Usuario usuario) {
        final Optional<Usuario> result = usuarioRepository.findByUsername(usuario.getUsername());

        if (result.isEmpty()) {
            return usuarioRepository.save(usuario);
        }

        return result.get();
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR"})
    public void deleteUsuario(int userId, Long idUsuarioElimina) {
        Usuario usuario = getUserById(userId);
        usuario.setEstado(EntityStateEnum.INACTIVO.getEstado());
        usuario.setFechaModifica(LocalDateTime.now());
        usuario.setUsuarioModifica(idUsuarioElimina);
        usuarioRepository.save(usuario);
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR"})
    public void activarUsuario(int userId, Long idUsuarioElimina) {
        Usuario usuario = getUserById(userId);
        usuario.setEstado(EntityStateEnum.ACTIVO.getEstado());
        usuario.setFechaModifica(LocalDateTime.now());
        usuario.setUsuarioModifica(idUsuarioElimina);
        usuarioRepository.save(usuario);
    }

    @Secured("ROLE_ADMINISTRADOR")
    public Usuario getUserById(int userId) {
        return usuarioRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(messageSource.getMessage(
                        "{service.usuario.no.existe}", null, null,
                        LocaleContextHolder.getLocale().stripExtensions()), userId)));
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Usuario getUserByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format(messageSource.getMessage("service.usuario.no.existe", null, null,
                                LocaleContextHolder.getLocale().stripExtensions()), username)));
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    public Page<Usuario> getUsuarios(int page, int size) {
        return usuarioRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "username")));
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    public List<Usuario> getUsuariosByRol(String rol) {
        return permisoRepository.findUsersByRol(rol);
    }
}
