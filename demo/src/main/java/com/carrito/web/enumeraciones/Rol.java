package com.carrito.web.enumeraciones;

public enum Rol {
    ROLE_ADMINISTRADOR ("Administrador"),
    ROLE_NORMAL ("Normal"),
    ROLE_VIP ("VIP");

    private Rol(String texto){
        this.texto = texto;
    }

    private String texto;

    public String getTexto(){
        return texto;
    }
}
