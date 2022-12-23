package com.devsu.cuenta.bancaria;

import com.devsu.cuenta.bancaria.business.buider.ClienteBuilder;
import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.business.services.cuenta.ClienteService;
import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ClienteTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeAll
    public void init() {
        Page<Usuario> usuarios = usuarioService.getUsuarios(0, 10);
        assertEquals(4L, usuarios.getTotalElements());

        usuario = usuarioService.getUserByUsername("admin");
        assertEquals(1L, usuario.getId());
    }

    @Test
    public void testCreateCliente() {
        Cliente cliente = ClienteBuilder.newBuilder()
                .usuarioCrea(usuario.getId())
                .nombre("nombre cliente")
                .telefono("0123456789")
                .direccion("direccion cliente")
                .genero("M")
                .edad(39)
                .identificacion("0123456789")
                .usuario(usuario)
                .build();

        clienteService.crearCliente(cliente);
        Page<Cliente> clientes = clienteService.findAllByEstado(0, 10);

        assertEquals(1L, clientes.getTotalElements());
    }

    @Test
    public void  testCrearClienteYaExiste() {
        Cliente cliente = ClienteBuilder.newBuilder()
                .usuarioCrea(usuario.getId())
                .nombre("nombre cliente")
                .telefono("0123456789")
                .direccion("direccion cliente")
                .genero("M")
                .edad(39)
                .identificacion("0123456789")
                .usuario(usuario)
                .build();

        clienteService.crearCliente(cliente);

        CuentaException cuentaException = assertThrows(CuentaException.class,
                () -> clienteService.crearCliente(cliente));

        assertEquals("El cliente con la identificación 0123456789 ya existe.", cuentaException.getMessage());

    }

}
