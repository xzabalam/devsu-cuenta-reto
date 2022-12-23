package com.devsu.cuenta.bancaria.business.exceptions;

public class SaldoInsuficienteException extends DomainException {
    public SaldoInsuficienteException() {
        super();
    }

    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
