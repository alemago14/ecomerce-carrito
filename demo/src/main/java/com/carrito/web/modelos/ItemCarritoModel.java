package com.carrito.web.modelos;

import lombok.Data;

@Data
public class ItemCarritoModel {
    
    private String id;
    private String idCarrito;
    private String idProducto;
    private int cantidad;
}
