package com.devsu.cuenta.bancaria.business.buider;

import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;

import java.time.LocalDateTime;

public class ClienteBuilder {
    private static Cliente cliente;

    public static ClienteBuilder newBuilder() {
        cliente = new Cliente();
        return new ClienteBuilder();
    }

    public Cliente build() {
        cliente.setFechaCrea(LocalDateTime.now());
        cliente.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return cliente;
    }

    public ClienteBuilder identificacion(String identificacion) {
        cliente.setIdentificacion(identificacion);
        return this;
    }

    public ClienteBuilder genero(String genero) {
        cliente.setGenero(genero);
        return this;
    }

    public ClienteBuilder edad(Integer edad) {
        cliente.setEdad(edad);
        return this;
    }

    public ClienteBuilder direccion(String direccion) {
        cliente.setDireccion(direccion);
        return this;
    }

    public ClienteBuilder telefono(String telefono) {
        cliente.setTelefono(telefono);
        return this;
    }

    public ClienteBuilder usuario(Usuario usuario) {
        cliente.setUsuario(usuario);
        return this;
    }

    public ClienteBuilder nombre(String nombre) {
        cliente.setNombre(nombre);
        return this;
    }

    public ClienteBuilder usuarioCrea(Long idUsuarioCrea) {
        cliente.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
