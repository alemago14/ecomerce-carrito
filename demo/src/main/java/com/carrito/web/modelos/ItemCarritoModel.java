package com.carrito.web.modelos;

import lombok.Data;

@Data
public class ItemCarritoModel {
    
    private String idProducto;
    private String nombre;
    private int cantidad;
}
