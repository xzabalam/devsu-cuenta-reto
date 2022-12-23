package com.devsu.cuenta.bancaria.data.entities.cuenta;

public interface Persona {
    String getNombre();

    void setNombre(String nombre);

    String getGenero();

    void setGenero(String genero);

    Integer getEdad();

    void setEdad(Integer edad);

    String getIdentificacion();

    void setIdentificacion(String identificacion);

    String getDireccion();

    void setDireccion(String direccion);

    String getTelefono();

    void setTelefono(String telefono);
}
