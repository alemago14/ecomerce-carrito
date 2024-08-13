package com.carrito.web.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.web.modelos.ItemCarritoModel;
import com.carrito.web.servicios.ItemCarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController("/api/v1/items")
@Tag(name = "Items de carrrito", description = "Gestión de los items del carrito")
public class ItemCarritoController {
    
    @Autowired
    private ItemCarritoService itemCarritoService;
    
}
