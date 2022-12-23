package com.devsu.cuenta.bancaria.web.controllers.cuenta;

import com.devsu.cuenta.bancaria.business.services.cuenta.ClienteService;
import com.devsu.cuenta.bancaria.business.services.cuenta.CuentaService;
import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cuenta;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.CuentaTo;
import com.devsu.cuenta.bancaria.web.mappers.CuentaToEntityMapper;
import com.devsu.cuenta.bancaria.web.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/cuentas")
public class CuentasController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;

    public CuentasController(ClienteService clienteService, UsuarioService usuarioService, CuentaService cuentaService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Permite obtener una cuenta por el id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CuentaTo> getById(@PathVariable("id") Long id) {
        RestPreconditions.checkNull(id);

        Cuenta cuenta = cuentaService.obtenerCuentaPorId(id);
        return new ResponseEntity<>(CuentaToEntityMapper.convertirToCuentaTo(cuenta), HttpStatus.OK);
    }

    // crear cuenta
    @PostMapping("/cliente/{idCliente}")
    @Operation(summary = "Permite crear una cuenta de ahorro o corriente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntityModel<CuentaTo>> crearCuenta(
            @PathVariable("idCliente") Long idCliente, @Valid @RequestBody CuentaTo cuentaTo,
            Authentication authentication) {
        RestPreconditions.checkNull(idCliente);
        RestPreconditions.checkNull(cuentaTo);

        Cliente cliente = clienteService.findById(idCliente);
        Usuario usuario = usuarioService.getUserByUsername(authentication.getName());
        Cuenta cuentaCreada = cuentaService.crearCuenta(CuentaToEntityMapper.convertToCuenta(cuentaTo, cliente, usuario));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cuentaCreada.getId()).toUri();
        EntityModel<CuentaTo> recursoCuenta = EntityModel.of(CuentaToEntityMapper.convertirToCuentaTo(cuentaCreada));
        recursoCuenta.add(linkTo(methodOn(CuentasController.class).getById(cuentaCreada.getId())).withSelfRel());
        return ResponseEntity.created(location).body(recursoCuenta);
    }
}
