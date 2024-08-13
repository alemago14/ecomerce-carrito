package com.carrito.web.servicios;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrito.web.convertidores.FechasPromocionalesConverter;
import com.carrito.web.excepciones.WebException;

@Service
public class FechaService {
    
    @Autowired
    private FechasPromocionalesConverter fechasPromocionalesConverter;

    public LocalDate convertirFecha(String cadena) throws WebException{
        LocalDate fecha = null;

        validarDatos(cadena);

        fecha = fechasPromocionalesConverter.convertirAFecha(cadena);

        return fecha;
    }

    public void validarDatos(String cadena) throws WebException{
        if(cadena == null || cadena.isEmpty()){
            throw new WebException("Debe ingresar una fecha");
        }
    }
}
