package com.itpatagonia.Buhoristeca.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Integer idRole) {
        super("El rol con id " + idRole + " no existe");
    }
}
