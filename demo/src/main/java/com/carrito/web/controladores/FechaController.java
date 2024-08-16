package com.carrito.web.controladores;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.web.servicios.FechaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/fecha")
@Tag(name="Fecha actual", description = "Gestion para ingresar la fecha que va a usar el sistema durante la sesion.")
public class FechaController {
    
    @Autowired
    private FechaService fechaService;

    @PostMapping("/ingresarActual")
    @Operation(summary = "Setea la fecha que va a usar el sistema", description = "Permite ingresar una fecha en formato string 'dd/MM/yyyy' que se seteara en la sesion y la usara el sistema.")
    public ResponseEntity<Object> ingresarFecha(HttpSession session, @RequestParam String cadena) {
        LocalDate fecha = null;
        try {
            fecha = fechaService.convertirFecha(cadena);
            session.setAttribute("fechaActual", fecha);
            return new ResponseEntity<>("Fecha nueva ingresada =" + cadena, HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/obtenerActual")
    @Operation(summary = "Obtiene la fecha seteada en el sistema", description = "Devuelve la fecha guardada en la session si tiene una fecha guardada sino devuelve null")
    public LocalDate obtenerFechaActual(HttpSession session) {
        LocalDate fechaActual = (LocalDate) session.getAttribute("fechaActual");
        if(fechaActual == null){
            return LocalDate.now();
        }
        return fechaActual;
    }
    
    
}
