package com.devsu.cuenta.bancaria.data.entities.core;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Dated extends Serializable {
    LocalDateTime getFechaCrea();

    void setFechaCrea(LocalDateTime fechaCrea);

    LocalDateTime getFechaModifica();

    void setFechaModifica(LocalDateTime fechaModifica);
}
