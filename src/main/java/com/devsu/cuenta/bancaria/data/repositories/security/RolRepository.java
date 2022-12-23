package com.devsu.cuenta.bancaria.data.repositories.security;

import java.util.Optional;

import com.devsu.cuenta.bancaria.data.entities.security.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
	Optional<Rol> findByNombre(String nombre);
}
