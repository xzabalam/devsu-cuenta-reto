package com.devsu.cuenta.bancaria.web.util;

import com.devsu.cuenta.bancaria.web.exceptions.MyResourceNotFoundException;

public final class RestPreconditions {
    private RestPreconditions() throws ReflectiveOperationException {
        throw new ReflectiveOperationException("Instances of this type are forbidden.");
    }

    public static <T> T checkNull(final T resource) {
        if (resource == null) {
            throw new MyResourceNotFoundException("El parametro de entrada no puede ser nulo.");
        }

        return resource;
    }

    public static <T> T checkEmptyString(final T resource) {
        checkNull(resource);

        if (resource instanceof String) {
            if (((String) resource).trim().isEmpty()) {
                throw new MyResourceNotFoundException("Se esperaba un valor.");
            }
        }

        return resource;
    }
}
