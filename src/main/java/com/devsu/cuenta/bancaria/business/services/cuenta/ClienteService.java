package com.devsu.cuenta.bancaria.business.services.cuenta;

import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import com.devsu.cuenta.bancaria.data.repositories.cuenta.ClienteRepository;
import com.devsu.cuenta.bancaria.web.dtos.ClienteTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR"})
    public Cliente crearCliente(Cliente cliente) {
        Optional<Cliente> optionalCliente = clienteRepository.findByIdentificacion(cliente.getIdentificacion());

        if (optionalCliente.isPresent()) {
            throw new CuentaException("El cliente con la identificación " + cliente.getIdentificacion() + " ya existe" +
                    ".");
        }

        return clienteRepository.save(cliente);
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cliente findByIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacionAndEstado(identificacion,
                EntityStateEnum.ACTIVO.getEstado()).orElseThrow(() -> new CuentaException("No existe el cliente con " +
                "la identificación: " + identificacion));
    }

    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new CuentaException("No existe el cliente con " +
                "la id: " + id));
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    public Page<Cliente> findAllByEstado(int page, int size) {
        return clienteRepository.findAllByEstado(EntityStateEnum.ACTIVO.getEstado(), PageRequest.of(page, size));
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_USUARIO"})
    public Cliente actualizarCliente(Long idCliente, ClienteTo clienteTo, Usuario usuario) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new CuentaException("No existe el " +
                "cliente con el id: " + idCliente));

        cliente.setNombre(clienteTo.getNombre());
        cliente.setIdentificacion(clienteTo.getIdentificacion());
        cliente.setGenero(clienteTo.getGenero());
        cliente.setEdad(clienteTo.getEdad());
        cliente.setDireccion(clienteTo.getDireccion());
        cliente.setTelefono(clienteTo.getTelefono());
        cliente.setUsuarioModifica(usuario.getId());
        cliente.setFechaModifica(LocalDateTime.now());

        return clienteRepository.save(cliente);
    }

    @Transactional
    @Secured({"ROLE_ADMINISTRADOR"})
    public void eliminarCliente(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new CuentaException("No existe el " +
                "cliente con el id: " + idCliente));

        clienteRepository.delete(cliente);
    }
}
