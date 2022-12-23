package com.devsu.cuenta.bancaria.data.entities.security;

import com.devsu.cuenta.bancaria.data.entities.core.impl.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Getter
@Setter
public class Usuario extends AbstractEntity {

    @NotEmpty
    @Column(name = "username", nullable = false)
    @Size(min = 5, max = 25, message = "{usuario.entity.auditoria.username}")
    private String username;

    @NotEmpty
    @JsonIgnore
    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 20, message = "{usuario.entity.auditoria.password}")
    private String password;
}
