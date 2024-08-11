package com.carrito.web.modelos;

import lombok.Data;

@Data
public class ProductoModel {
    
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
}
