package com.carrito.web.modelos;

import java.util.ArrayList;
import java.util.List;

import com.carrito.web.enumeraciones.TipoCarrito;

import lombok.Data;

@Data
public class CarritoModel {
    
    private String id;
    private String fecha;
    private TipoCarrito tipoCarrito;
    private List<ItemCarritoModel> items = new ArrayList<>();
    private double descuento;
    private double totalParcial;
    private double total;

}
