package com.carrito.web.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrito.web.convertidores.ProductoConverter;
import com.carrito.web.entidades.Producto;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.ProductoModel;
import com.carrito.web.repositorios.ProductoRepository;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoConverter productoConverter;

    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }

    public Producto buscarPorId(String id){
        return productoRepository.getReferenceById(id);
    }

    public Producto guardar(ProductoModel model) throws WebException{
        Producto producto = new Producto();
        model.setId(null);
        producto = productoConverter.modeloToEntidad(model);
        return productoRepository.save(producto);
    }
}
