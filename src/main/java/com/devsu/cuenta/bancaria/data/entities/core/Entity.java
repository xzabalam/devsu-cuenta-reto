package com.devsu.cuenta.bancaria.data.entities.core;

import java.io.Serializable;

public interface Entity extends Serializable {

	Long getId();

	void setId(Long id);

	String getEstado();

	void setEstado(String estado);
}
