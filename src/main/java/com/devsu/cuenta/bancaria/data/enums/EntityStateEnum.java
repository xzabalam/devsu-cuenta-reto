package com.devsu.cuenta.bancaria.data.enums;

public enum EntityStateEnum {
    ACTIVO("A"), INACTIVO("I");

    private String estado;

    EntityStateEnum(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
