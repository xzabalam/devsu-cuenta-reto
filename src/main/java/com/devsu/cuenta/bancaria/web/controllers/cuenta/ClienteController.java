package com.devsu.cuenta.bancaria.web.controllers.cuenta;

import com.devsu.cuenta.bancaria.business.services.cuenta.ClienteService;
import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.cuenta.Cliente;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.ClienteTo;
import com.devsu.cuenta.bancaria.web.mappers.ClienteToEntityMapper;
import com.devsu.cuenta.bancaria.web.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
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
@RequestMapping("/clientes")
public class ClienteController {

    private final UsuarioService usuarioService;
    private final ClienteService clienteService;

    public ClienteController(UsuarioService usuarioService, ClienteService clienteService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Permite obtener un cliente por el id.")
    public ResponseEntity<Cliente> getById(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.findById(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Permite obtener el listado paginado de todas las cuentas existentes")
    public ResponseEntity<Page<Cliente>> getAllClientes(
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "500") int size
    ) {
        Page<Cliente> clientes = clienteService.findAllByEstado(page, size);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Permite crear un nuevo cliente")
    public ResponseEntity<EntityModel<ClienteTo>> crearCliente(
            @Valid @RequestBody ClienteTo clienteTo, Authentication authentication) {
        RestPreconditions.checkNull(clienteTo);

        Usuario usuario = usuarioService.getUserByUsername(authentication.getName());
        Cliente clienteCreado = clienteService.crearCliente(ClienteToEntityMapper.convertToCliente(clienteTo, usuario));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteCreado.getId()).toUri();
        EntityModel<ClienteTo> recursoCliente = EntityModel.of(ClienteToEntityMapper.convertToClienteTo(clienteCreado));
        recursoCliente.add(linkTo(methodOn(ClienteController.class).getById(clienteCreado.getId())).withSelfRel());
        return ResponseEntity.created(location).body(recursoCliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Permite actualizar un cliente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClienteTo> actualizarCliente(@PathVariable("id") Long id, @Valid @RequestBody ClienteTo clienteTo, Authentication authentication) {
        RestPreconditions.checkNull(clienteTo);
        RestPreconditions.checkNull(id);
        Usuario usuario = usuarioService.getUserByUsername(authentication.getName());
        Cliente cliente = clienteService.actualizarCliente(id, clienteTo, usuario);

        return new ResponseEntity<>(ClienteToEntityMapper.convertToClienteTo(cliente), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Permite eliminar un cliente por su id.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCliente(@PathVariable("id") Long id) {
        RestPreconditions.checkNull(id);
        clienteService.eliminarCliente(id);
    }
}
