package com.devsu.cuenta.bancaria.business.services.security;

import com.devsu.cuenta.bancaria.data.entities.security.Permiso;
import com.devsu.cuenta.bancaria.data.entities.security.Rol;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.repositories.security.PermisoRepository;
import com.devsu.cuenta.bancaria.data.repositories.security.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioRolService {

    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;

	public UsuarioRolService(PermisoRepository permisoRepository, RolRepository rolRepository) {
		this.permisoRepository = permisoRepository;
		this.rolRepository = rolRepository;
	}

	@Transactional
    public Permiso crearUsuarioRol(Usuario usuario, Rol rol, Long idUsuarioCrea) {
        final Optional<Permiso> usuarioRol = permisoRepository.findByUsernameAndRol(usuario.getUsername(),
                rol.getNombre());

        if (usuarioRol.isEmpty()) {
            final Permiso permisoNuevo = new Permiso(usuario, rol);
            permisoNuevo.setUsuarioCrea(idUsuarioCrea);
            return permisoRepository.save(permisoNuevo);
        }

        return usuarioRol.get();
    }

    @Transactional
    public void deleteUserByUsuarioId(Long usuarioId) {
        final List<Permiso> usuarioRoles = permisoRepository.findByUsuarioId(usuarioId);
        usuarioRoles.forEach(permiso -> permisoRepository.delete(permiso));
    }
}
