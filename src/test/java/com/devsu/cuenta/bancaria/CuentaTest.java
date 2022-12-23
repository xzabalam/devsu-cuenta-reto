package com.devsu.cuenta.bancaria;

import com.devsu.cuenta.bancaria.business.buider.ClienteBuilder;
import com.devsu.cuenta.bancaria.business.buider.CuentaBuilder;
import com.devsu.cuenta.bancaria.business.exceptions.CuentaException;
import com.devsu.cuenta.bancaria.business.services.cuenta.ClienteService;
import com.devsu.cuenta.bancaria.business.services.cuenta.CuentaService;
import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.data.enums.TipoCuentaEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class CuentaTest {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Cliente cliente;

    @Autowired
    private ClienteService clienteService;

    @BeforeAll
    public void init() {
        usuario = usuarioService.getUserByUsername("admin");
        assertEquals(1L, usuario.getId());

        Cliente clienteParaCrear = ClienteBuilder.newBuilder()
                .usuarioCrea(usuario.getId())
                .nombre("nombre cliente")
                .telefono("0123456710")
                .direccion("direccion cliente")
                .genero("M")
                .edad(39)
                .identificacion("0123456789")
                .usuario(usuario)
                .build();

        cliente = clienteService.crearCliente(clienteParaCrear);
    }

    @Test
    public void testCrearCuentaAhorro() {
        Cuenta cuenta = CuentaBuilder.newBuilder()
                .saldoInicial(BigDecimal.valueOf(100))
                .tipoCuenta(TipoCuentaEnum.AHORRO)
                .numeroCuenta("0123456789")
                .usuarioCrea(usuario.getId())
                .cliente(cliente)
                .build();
        Cuenta cuentaCreada = cuentaService.crearCuenta(cuenta);
        assertNotNull(cuentaCreada.getId());
    }

    @Test
    public void testCrearCuentaCorriente() {
        Cuenta cuenta = CuentaBuilder.newBuilder()
                .saldoInicial(BigDecimal.valueOf(200))
                .tipoCuenta(TipoCuentaEnum.CORRIENTE)
                .numeroCuenta("0123456781")
                .usuarioCrea(usuario.getId())
                .cliente(cliente)
                .build();
        Cuenta cuentaCreada = cuentaService.crearCuenta(cuenta);
        assertNotNull(cuentaCreada.getId());
    }

    @Test
    public void testDeberiaNoPermitirCuentaDuplicada() {
        Cuenta cuenta = CuentaBuilder.newBuilder()
                .saldoInicial(BigDecimal.valueOf(200))
                .tipoCuenta(TipoCuentaEnum.CORRIENTE)
                .numeroCuenta("0123456782")
                .usuarioCrea(usuario.getId())
                .cliente(cliente)
                .build();
        cuentaService.crearCuenta(cuenta);

        CuentaException cuentaException = assertThrows(CuentaException.class,
                () -> cuentaService.crearCuenta(cuenta));

        assertEquals("La cuenta con el número 0123456782 ya existe.", cuentaException.getMessage());

    }

    @Test
    public void testDeberiaGenerarErrorAlCrearCuentaConTipoInvalido() {
        Cuenta cuenta = CuentaBuilder.newBuilder()
                .saldoInicial(BigDecimal.valueOf(100))
                .tipoCuenta(Mockito.any())
                .numeroCuenta("0123456789")
                .usuarioCrea(usuario.getId())
                .cliente(cliente)
                .build();

        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class,
                () -> cuentaService.crearCuenta(cuenta));

        assertTrue(constraintViolationException.getMessage().contains("cuenta.tipoCuenta.notnull"));
    }

}
