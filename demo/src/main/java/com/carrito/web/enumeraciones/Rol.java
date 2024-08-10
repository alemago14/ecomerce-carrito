package com.carrito.web.enumeraciones;

public enum Rol {
    ADMINISTRADOR ("Administrador"),
    NORMAL ("Normal"),
    VIP ("VIP");

    private Rol(String texto){
        this.texto = texto;
    }

    private String texto;

    public String getTexto(){
        return texto;
    }
}
