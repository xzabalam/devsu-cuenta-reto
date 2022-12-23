package com.devsu.cuenta.bancaria.data.entities.security;

import com.devsu.cuenta.bancaria.data.entities.core.impl.AbstractNamed;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "rol", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class Rol extends AbstractNamed {

}
