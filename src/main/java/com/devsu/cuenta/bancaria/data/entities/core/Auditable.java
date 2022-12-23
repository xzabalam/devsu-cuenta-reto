package com.devsu.cuenta.bancaria.data.entities.core;

public interface Auditable extends Dated {
	Long getUsuarioCrea();

	Long getUsuarioModifica();

	void setUsuarioCrea(Long usuarioCrea);

	void setUsuarioModifica(Long usuarioModifica);
}
