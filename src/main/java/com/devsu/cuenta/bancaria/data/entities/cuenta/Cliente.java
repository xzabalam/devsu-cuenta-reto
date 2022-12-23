package com.devsu.cuenta.bancaria.data.entities.cuenta;

import com.devsu.cuenta.bancaria.data.entities.core.impl.AbstractNamed;
import com.devsu.cuenta.bancaria.data.entities.security.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente extends AbstractNamed implements Persona {

    @Column(name = "identificacion")
    @NotNull(message = "{cliente.identificacion.notnull}")
    @Size(min = 10, max = 10, message = "{cliente.identificacion.size}")
    private String identificacion;

    @Column(name = "genero")
    @NotNull(message = "{cliente.genero.notnull}")
    @Size(min = 1, max = 1, message = "{cliente.genero.size}")
    private String genero;

    @Column(name = "edad")
    @NotNull(message = "{cliente.edad.notnull}")
    @Min(value = 18, message = "{cliente.edad.min}")
    private Integer edad;

    @Column(name = "direccion")
    @NotNull(message = "{cliente.direccion.notnull}")
    @Size(max = 200, message = "{cliente.direccion.size}")
    private String direccion;

    @Column(name = "telefono")
    @NotNull(message = "{cliente.telefono.notnull}")
    @Size(max = 20, message = "{cliente.telefono.size}")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "{telefono.pattern}")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "{cliente.usuario.notnull}")
    @JsonIgnore
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getGenero() {
        return genero;
    }

    @Override
    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public Integer getEdad() {
        return edad;
    }

    @Override
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public String getIdentificacion() {
        return identificacion;
    }

    @Override
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String getDireccion() {
        return direccion;
    }

    @Override
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
