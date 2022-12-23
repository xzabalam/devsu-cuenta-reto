package com.devsu.cuenta.bancaria.business.services.security;

import com.devsu.cuenta.bancaria.data.entities.security.Rol;
import com.devsu.cuenta.bancaria.data.repositories.security.RolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional
    public Rol createRol(Rol rol) {
        final Optional<Rol> result = rolRepository.findByNombre(rol.getNombre());

        if (result.isPresent()) {
            return result.get();
        }

        return rolRepository.save(rol);
    }

    public void deleteRol(Long rolId) {
        final Optional<Rol> result = rolRepository.findById(rolId);

        if (result.isPresent()) {
            rolRepository.delete(result.get());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("{service.rol.no.existe}", rolId));
    }

    public Page<Rol> getRoles(int page, int size) {
        return rolRepository.findAll(PageRequest.of(page, size));
    }

    public Rol updateRol(Long rolId, Rol rol) {
        final Optional<Rol> result = rolRepository.findById(rolId);

        if (result.isPresent()) {
            return rolRepository.save(rol);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("{service.rol.no.existe}", rolId));
    }
}
