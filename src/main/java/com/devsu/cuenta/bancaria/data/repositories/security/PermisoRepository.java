package com.devsu.cuenta.bancaria.data.repositories.security;

import com.devsu.cuenta.bancaria.data.entities.security.Permiso;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    List<Permiso> findByUsuarioId(Long usuarioId);

    @Query("select ur from Permiso ur where ur.usuario.username = ?1")
    List<Permiso> findByUsername(String username);

    @Query("select ur from Permiso ur where ur.usuario.username = ?1 and ur.rol.nombre = ?2")
    Optional<Permiso> findByUsernameAndRol(String username, String rol);

    @Query("select ur.usuario from Permiso ur where ur.rol.nombre = ?1")
    List<Usuario> findUsersByRol(String rol);
}
