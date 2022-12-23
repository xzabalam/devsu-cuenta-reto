package com.devsu.cuenta.bancaria.data.entities.core.impl;


import com.devsu.cuenta.bancaria.data.entities.core.Named;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractNamed extends AbstractEntity implements Named {
    private static final long serialVersionUID = -412218241272244614L;

    @NotEmpty(message = "{cliente.nombre.notnull}")
    @NotNull(message = "{cliente.nombre.notnull}")
    @Size(min = 2, max = 100, message = "{cliente.nombre.size}")
    @Column(name = "nombre")
    private String nombre;

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
