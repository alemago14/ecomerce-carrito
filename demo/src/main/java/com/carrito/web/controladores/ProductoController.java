package com.carrito.web.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.web.entidades.Producto;
import com.carrito.web.modelos.ProductoModel;
import com.carrito.web.servicios.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("listar")
    @Operation(summary = "Obtener todos los productos", description = "Devuelve un listado con todos los productos en la base de datos")
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping("nuevo")
    @Operation(summary = "Crea un nuevo producto en la base de datos", description = "Guarda un nuevo producto a partir de un producto model")
    public ResponseEntity<Object> nuevoProducto(@RequestBody ProductoModel model) {
       try {
        productoService.guardar(model);
        return new ResponseEntity<>("Producto nuevo ingresado", HttpStatus.OK);
       } catch (Exception e) {
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }
    
    
}
