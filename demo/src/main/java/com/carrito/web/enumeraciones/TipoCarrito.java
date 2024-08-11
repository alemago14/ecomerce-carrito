package com.carrito.web.enumeraciones;

public enum TipoCarrito {
    
    NORMAL("Normal"), 
    PROMOCIONAL("Promocional"), 
    VIP("Vip");

    private TipoCarrito(String texto){
        this.texto = texto;
    }

    private String texto;

    public String getTexto(){
        return texto;
    }
}
