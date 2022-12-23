package com.devsu.cuenta.bancaria.data.entities.security;

import com.devsu.cuenta.bancaria.data.entities.core.impl.AbstractEntity;
import com.devsu.cuenta.bancaria.data.enums.EntityStateEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "permiso")
@Getter
@Setter
public class Permiso extends AbstractEntity {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    private Rol rol;

    public Permiso() {
    }

    public Permiso(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
        this.setFechaCrea(LocalDateTime.now());
        this.setEstado(EntityStateEnum.ACTIVO.getEstado());
    }
}
