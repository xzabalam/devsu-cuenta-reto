package com.devsu.cuenta.bancaria.web.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
public class ClienteTo {

    @NotEmpty(message = "{cliente.nombre.notnull}")
    @NotNull(message = "{cliente.nombre.notnull}")
    @Size(min = 2, max = 100, message = "{cliente.nombre.size}")
    private String nombre;

    @NotNull(message = "{cliente.identificacion.notnull}")
    @Size(min = 10, max = 10, message = "{cliente.identificacion.size}")
    private String identificacion;

    @NotNull(message = "{cliente.genero.notnull}")
    @Size(min = 1, max = 1, message = "{cliente.genero.size}")
    private String genero;

    @NotNull(message = "{cliente.edad.notnull}")
    @Min(value = 18, message = "{cliente.edad.min}")
    private Integer edad;

    @NotNull(message = "{cliente.direccion.notnull}")
    @Size(max = 200, message = "{cliente.direccion.size}")
    private String direccion;

    @NotNull(message = "{cliente.telefono.notnull}")
    @Size(max = 20, message = "{cliente.telefono.size}")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "{telefono.pattern}")
    private String telefono;
}
