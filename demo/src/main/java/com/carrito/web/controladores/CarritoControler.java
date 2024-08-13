package com.carrito.web.controladores;

import org.springframework.web.bind.annotation.RestController;

import com.carrito.web.entidades.Usuario;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.CarritoModel;
import com.carrito.web.modelos.ItemCarritoModel;
import com.carrito.web.servicios.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController("/api/v1/carrito")
@Tag(name = "Carrito", description = "Gestión de carritos")
public class CarritoControler {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/sumarItem")
    @Operation(summary = "Agrega un item al carito existente.", description = "Agrega un item al carrito existente al carito de la sesion.")
    public ResponseEntity<Object> sumarItem(@RequestBody ItemCarritoModel itemModel, HttpSession session, Authentication authentication){
        LocalDate fechaActual = (LocalDate) session.getAttribute("fechaActual");
        CarritoModel carritoModel = (CarritoModel) session.getAttribute("carrito");
        Usuario usuario = (Usuario) authentication.getPrincipal();
        try {
            if(carritoModel == null){
                carritoModel = new CarritoModel();
                carritoModel = carritoService.agregarItem(itemModel, carritoModel, fechaActual, usuario);
                session.setAttribute("carrito", carritoModel);
            }else{
                carritoModel = carritoService.agregarItem(itemModel, carritoModel, fechaActual, usuario);
                session.setAttribute("carrito", carritoModel);
            }
            return new ResponseEntity<>("Item agregado", HttpStatus.CREATED);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtenerCarrito")
    @Operation(summary = "Devuelve el carito actual de la session", description = "Devuelve el CarritoModel actual de la session")
    public CarritoModel obtenerCarrito(HttpSession session) {
        return (CarritoModel) session.getAttribute("carrito");
    }

    @PostMapping("/quitarItem")
    @Operation(summary = "Quita un item al carito existente.", description = "Quita el item seleccionado al carrito existente al carito de la sesion.")
    public ResponseEntity<Object> quitarItem(@RequestBody ItemCarritoModel itemModel, HttpSession session) {
        CarritoModel carritoModel = (CarritoModel) session.getAttribute("carrito");
        try {
            carritoModel = carritoService.quitarItem(carritoModel, itemModel);
            session.setAttribute("carrito", carritoModel);
            return new ResponseEntity<>("Item quitado", HttpStatus.CREATED);
        } catch (WebException e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/comprar")
    @Operation(summary = "Confirma la compra de un carrito", description = "Confirma la compra y guarda el carrito en la base de datos.")
    public ResponseEntity<Object> comprar(HttpSession session, Authentication authentication) {
        CarritoModel carritoModel = (CarritoModel) session.getAttribute("carrito");
        Usuario usuario = (Usuario) authentication.getPrincipal();

        try {
            carritoService.comprar(carritoModel, usuario);
            return new ResponseEntity<>("Carrito comprado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/limpiar")
    @Operation(summary = "Borra la información del carrito actual.", description = "Setea la informacion del carrito guardado en la sesion.")
    public ResponseEntity<Object> limpiarCarrito(HttpSession session) {
        CarritoModel carritoModel = new CarritoModel();
        session.setAttribute("carrito", carritoModel);
        return new ResponseEntity<>("Carrito Limpiado", HttpStatus.CREATED);
    }
    
    
    
}
