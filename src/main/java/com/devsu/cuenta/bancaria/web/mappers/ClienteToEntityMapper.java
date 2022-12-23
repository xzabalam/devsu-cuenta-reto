package com.devsu.cuenta.bancaria.web.mappers;

import com.devsu.cuenta.bancaria.business.buider.ClienteBuilder;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.ClienteTo;

public final class ClienteToEntityMapper {
    private ClienteToEntityMapper() throws ReflectiveOperationException {
        throw new ReflectiveOperationException("Instances of this type are forbidden.");
    }

    public static Cliente convertToCliente(ClienteTo clienteTo, Usuario usuario) {
        return ClienteBuilder.newBuilder()
                .edad(clienteTo.getEdad())
                .genero(clienteTo.getGenero())
                .identificacion(clienteTo.getIdentificacion())
                .direccion(clienteTo.getDireccion())
                .telefono(clienteTo.getTelefono())
                .usuario(usuario)
                .nombre(clienteTo.getNombre())
                .usuarioCrea(usuario.getId())
                .build();
    }

    public static final ClienteTo convertToClienteTo(Cliente cliente) {
        return ClienteTo.builder()
                .nombre(cliente.getNombre())
                .edad(cliente.getEdad())
                .direccion(cliente.getDireccion())
                .genero(cliente.getGenero())
                .telefono(cliente.getTelefono())
                .identificacion(cliente.getIdentificacion())
                .build();
    }
}
