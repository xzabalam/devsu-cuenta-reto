package com.devsu.cuenta.bancaria.web.controllers.security;

import com.devsu.cuenta.bancaria.business.services.security.UsuarioService;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.devsu.cuenta.bancaria.web.dtos.UsuarioTo;
import com.devsu.cuenta.bancaria.web.mappers.UsuarioToEntityMapper;
import com.devsu.cuenta.bancaria.web.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Permite obtener el usuario por el username, Roles permitidos Administrador y Usuario")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable("username") String username) {
        RestPreconditions.checkEmptyString(username);

        final Usuario usuario = usuarioService.getUserByUsername(username);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Permite obtener el listado paginado de usuarios, Roles permitidos Administrador.")
    public ResponseEntity<Page<Usuario>> getUsuarios(
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "500") int size
    ) {
        final Page<Usuario> usuarios = usuarioService.getUsuarios(page, size);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Permite crear un nuevo usuario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody @Valid UsuarioTo usuarioTo, Authentication authentication) {
        RestPreconditions.checkNull(usuarioTo);
        Usuario usuarioCrea = usuarioService.getUserByUsername(authentication.getName());
        Usuario usuario = UsuarioToEntityMapper.convertToUsuario(usuarioTo, usuarioCrea.getId());
        return new ResponseEntity<>(usuarioService.createUser(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Permite eliminar logicamente un usuario con base en su id")
    public void eliminarUsuario(@PathVariable("userid") int userId, Authentication authentication) {
        RestPreconditions.checkNull(userId);
        Usuario usuarioElimina = usuarioService.getUserByUsername(authentication.getName());
        usuarioService.deleteUsuario(userId, usuarioElimina.getId());
    }

    @PutMapping("/{userid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Permite activar logicamente un usuario con base en su id")
    public void activarUsuario(@PathVariable("userid") int userId, Authentication authentication) {
        RestPreconditions.checkNull(userId);
        Usuario usuarioElimina = usuarioService.getUserByUsername(authentication.getName());
        usuarioService.activarUsuario(userId, usuarioElimina.getId());
    }
}
