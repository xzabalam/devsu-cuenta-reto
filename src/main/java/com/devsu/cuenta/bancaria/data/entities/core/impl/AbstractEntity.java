package com.devsu.cuenta.bancaria.data.entities.core.impl;

import com.devsu.cuenta.bancaria.data.entities.core.Auditable;
import com.devsu.cuenta.bancaria.data.entities.core.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity implements Entity, Auditable {
    private static final long serialVersionUID = -412218241272244613L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_crea", nullable = false)
    @JsonIgnore
    private LocalDateTime fechaCrea;

    @Column(name = "fecha_modifica")
    @JsonIgnore
    private LocalDateTime fechaModifica;

    @Column(name = "id_usuario_crea", nullable = false)
    @JsonIgnore
    private Long usuarioCrea;

    @Column(name = "id_usuario_modifica")
    @JsonIgnore
    private Long usuarioModifica;

    @NotNull
    @NotEmpty
    @Column(name = "estado")
    private String estado;

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public LocalDateTime getFechaCrea() {
        return fechaCrea;
    }

    @Override
    public void setFechaCrea(LocalDateTime fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    @Override
    public LocalDateTime getFechaModifica() {
        return fechaModifica;
    }

    @Override
    public void setFechaModifica(LocalDateTime fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUsuarioCrea() {
        return usuarioCrea;
    }

    @Override
    public void setUsuarioCrea(Long usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    @Override
    public Long getUsuarioModifica() {
        return usuarioModifica;
    }

    @Override
    public void setUsuarioModifica(Long usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }


}
