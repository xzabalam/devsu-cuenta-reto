package com.devsu.cuenta.bancaria.business.exceptions;

public class CuentaException extends DomainException {
    public CuentaException() {
        super();
    }

    public CuentaException(String message) {
        super(message);
    }

    public CuentaException(String message, Throwable cause) {
        super(message, cause);
    }
}
