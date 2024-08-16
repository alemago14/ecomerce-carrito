package com.carrito.web.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.web.entidades.FechasPromocionales;
import com.carrito.web.entidades.Usuario;
import com.carrito.web.enumeraciones.Rol;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.FechasPromocionalesModel;
import com.carrito.web.servicios.FechasPromocionalesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/fechas")
@Tag(name = "Fechas Promocionales", description = "Gestión de Fechas Promocionales")
public class FechasPromocionalesController {
    
    @Autowired
    private FechasPromocionalesService fechasPromocionalesService;

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PostMapping("/nuevo")
    @Operation(summary = "Crear una nueva fecha promocional", description = "Guarda una nueva fecha promocional en la base de datos a partir de un fechaPromocionalModel")
    public ResponseEntity<Object> guardarFecha(@RequestBody FechasPromocionalesModel model) {
        try {
            model.setId(null);
            fechasPromocionalesService.guardarFecha(model);
            return new ResponseEntity<>("Fechas creado", HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista todas las fechas promocionales", description = "Devuelve una lista de fechas promocionales existentes en la base de datos")
    public List<FechasPromocionales> listarFechas() {
        return fechasPromocionalesService.listarFechas();
    }

    @GetMapping("/hayPromocion")
    @Operation(summary = "Confirma si la fecha ingresada entra en el periodo de promoción", description = "Devuelve un booleano si la fecha ingresada con formato dd/MM/yyyy esta dentro del periodo de una fecha promocional")
    public Boolean hayPromocion(@RequestParam String fechaActual) throws WebException {
        return fechasPromocionalesService.hayPromocion(fechaActual);
    }
    
    
    
}
