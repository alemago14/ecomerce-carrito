package com.carrito.web.convertidores;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carrito.web.entidades.ItemCarrito;
import com.carrito.web.entidades.Producto;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.ItemCarritoModel;
import com.carrito.web.servicios.ProductoService;

@Component
public class ItemCarritoConverter extends Convertidor<ItemCarritoModel, ItemCarrito>{

    @Autowired
    private ProductoService productoService;

    @Override
    public ItemCarrito modeloToEntidad(ItemCarritoModel m) throws WebException {
        ItemCarrito item = new ItemCarrito();

        try {
            BeanUtils.copyProperties(m, item);
        } catch (Exception e) {
            throw new WebException("Error al convertir el modelo a entidad.");
        }

        Producto producto = productoService.buscarPorId(m.getIdProducto());
        if(producto.equals(null)){
            throw new WebException("El producto no existe en la base de datos");
        }
        item.setProducto(producto);
        return item;
    }

    @Override
    public ItemCarritoModel entidadToModelo(ItemCarrito e) throws WebException {
        ItemCarritoModel model = new ItemCarritoModel();

        try {
            BeanUtils.copyProperties(e, model);
        } catch (Exception error) {
            throw new WebException("Error al convertir la entidad a modelo");
        }

        model.setIdProducto(e.getProducto().getId());
        return model;
    }
    
}
