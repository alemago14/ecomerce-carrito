package com.carrito.web.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrito.web.convertidores.FechasPromocionalesConverter;
import com.carrito.web.entidades.FechasPromocionales;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.FechasPromocionalesModel;
import com.carrito.web.repositorios.FechaPromocionalesRepository;

@Service
public class FechasPromocionalesService {
    
    @Autowired
    private FechaPromocionalesRepository fechaPromocionalesRepository;

    @Autowired
    private FechasPromocionalesConverter fechasPromocionalesConverter;

    public void validarDatos(FechasPromocionalesModel model) throws WebException{
        if(model.getNombre() == null || model.getNombre().isEmpty()){
            throw new WebException("El nombre de la fecha promocional no puede ser nulo.");
        }

        if(model.getFin() == null || model.getFin().isEmpty()){
            throw new WebException("La fecha promocional tiene que tenerr un fecha de fin.");
        }

        if(model.getInicio() == null || model.getInicio().isEmpty()){
            throw new WebException("La fecha promocional tiene que tenerr un fecha de inicio.");
        }
    }

    @Transactional
    public FechasPromocionales guardarFecha(FechasPromocionalesModel model) throws WebException{
        FechasPromocionales fechas = new FechasPromocionales();
        validarDatos(model);

        fechas = fechasPromocionalesConverter.modeloToEntidad(model);
        fechas = fechaPromocionalesRepository.save(fechas);
        return fechas;
    }

    public List<FechasPromocionales> listarFechas(){
        List<FechasPromocionales> fechas = new ArrayList<>();

        fechas = fechaPromocionalesRepository.findAll();

        return fechas;
    }

    public boolean hayPromocion(String cadena) throws WebException{
        if(cadena == null || cadena.isEmpty()){
            throw new WebException("Debe ingresa una fecha para comparar.");
        }

        LocalDate fecha = fechasPromocionalesConverter.convertirAFecha(cadena);
        List<FechasPromocionales> fechas = listarFechas();

        if(fechas.isEmpty()){
            return false;
        }

        return fechas.stream()
                 .anyMatch(promocion -> 
                     (fecha.isEqual(promocion.getFechaInicio()) || fecha.isAfter(promocion.getFechaInicio())) &&
                     (fecha.isEqual(promocion.getFechaFin()) || fecha.isBefore(promocion.getFechaFin())));
    }

    public boolean hayPromocion(LocalDate fecha){
        List<FechasPromocionales> fechas = listarFechas();
        return fechas.stream()
                 .anyMatch(promocion -> 
                     (fecha.isEqual(promocion.getFechaInicio()) || fecha.isAfter(promocion.getFechaInicio())) &&
                     (fecha.isEqual(promocion.getFechaFin()) || fecha.isBefore(promocion.getFechaFin())));
    }

    public LocalDate convertirAfecha(String cadena) throws WebException{
        return fechasPromocionalesConverter.convertirAFecha(cadena);
    }

    public String convertirAString(LocalDate fecha) throws WebException{
        return fechasPromocionalesConverter.convertirAString(fecha);
    }
}
