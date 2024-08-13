package com.carrito.web.convertidores;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carrito.web.entidades.FechasPromocionales;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.FechasPromocionalesModel;
import com.carrito.web.repositorios.FechaPromocionalesRepository;
import com.carrito.web.utilidades.FechasConversor;

@Component
public class FechasPromocionalesConverter extends Convertidor<FechasPromocionalesModel, FechasPromocionales> {

    @Autowired
    private FechaPromocionalesRepository fechaPromocionalesRepository;

    @Autowired
    private FechasConversor fechasConversor;

    @Override
    public FechasPromocionales modeloToEntidad(FechasPromocionalesModel m) throws WebException {
        FechasPromocionales fechas = new FechasPromocionales();
        if(m.getId() != null){
            return fechaPromocionalesRepository.getReferenceById(m.getId());
        }

        try {
            BeanUtils.copyProperties(m, fechas);
            fechas.setFechaInicio(fechasConversor.stringToLocalDate(m.getInicio()));
            fechas.setFechaFin(fechasConversor.stringToLocalDate(m.getFin()));
            return fechas;
        } catch (Exception e) {
            throw new WebException("Ocurrio un error al convertir el modelo, " + e);
        }
    }

    @Override
    public FechasPromocionalesModel entidadToModelo(FechasPromocionales e) throws WebException {
        FechasPromocionalesModel model = new FechasPromocionalesModel();

        try {
            BeanUtils.copyProperties(e, model);
            model.setInicio(fechasConversor.localDateToString(e.getFechaInicio()));
            model.setFin(fechasConversor.localDateToString(e.getFechaFin()));
            return model;
        } catch (Exception error) {
            throw new WebException("Ocurrio un error al convertir la entidad, " + error);
        }
    }

    public LocalDate convertirAFecha(String cadena) throws WebException{
        LocalDate fecha = null;
        try {
            fecha = fechasConversor.stringToLocalDate(cadena);
            return fecha;
        } catch (Exception e) {
            throw new WebException("Ocurrio un error al convertir la entidad, " + e);
        }
    }

    public String convertirAString(LocalDate fecha) throws WebException{
        String cadena = null;
        try {
            cadena = fechasConversor.localDateToString(fecha);
            return cadena;
        } catch (Exception e) {
            throw new WebException("Ocurrio un error al convertir la entidad, " + e);
        }
    }
    
}
