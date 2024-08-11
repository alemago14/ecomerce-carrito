package com.carrito.web.convertidores;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carrito.web.entidades.Producto;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.ProductoModel;
import com.carrito.web.repositorios.ProductoRepository;

@Component
public class ProductoConverter extends Convertidor<ProductoModel, Producto>{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto modeloToEntidad(ProductoModel m) throws WebException {
        Producto producto = new Producto();

        if(!(m.getId() == null || m.getId().isEmpty())){
            producto = productoRepository.getReferenceById(m.getId());
        }else{
            BeanUtils.copyProperties(m, producto);
        }

        return producto;
    }

    @Override
    public ProductoModel entidadToModelo(Producto e) throws WebException {
        try {
            ProductoModel productoModel = new ProductoModel();
            BeanUtils.copyProperties(e, productoModel);
            return productoModel;
        } catch (Exception error) {
            throw new WebException("Error al convertir el modelo a entidad");
        }
    }
    
}
