package com.devsu.cuenta.bancaria.data.repositories.security;

import java.util.Optional;

import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByUsername(String username);
}
