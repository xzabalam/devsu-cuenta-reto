package com.devsu.cuenta.bancaria.web.controllers.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AuthController {

    /**
     * Permite iniciar una sesión básica desde el formulario login del frontend y devuelve los roles que el usuario
     * tienen asignado.
     *
     * @param authentication
     * @return
     */
    @GetMapping("/auth")
    public ResponseEntity<Collection> validateLogin(Authentication authentication) {
        return new ResponseEntity<>(authentication.getAuthorities(), HttpStatus.OK);
    }
}
