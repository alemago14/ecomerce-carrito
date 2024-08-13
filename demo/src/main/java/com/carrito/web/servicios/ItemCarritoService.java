package com.carrito.web.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrito.web.convertidores.ItemCarritoConverter;
import com.carrito.web.entidades.Carrito;
import com.carrito.web.entidades.ItemCarrito;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.ItemCarritoModel;
import com.carrito.web.repositorios.ItemCarritoRepository;

@Service
public class ItemCarritoService {
    
    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ItemCarritoConverter itemCarritoConverter;

    public void validarDatos(ItemCarritoModel model) throws WebException{

        if(model.getCantidad() <= 0){
            throw new WebException("La cantidad de producto debe ser mayor a 0.");
        }
    }

    @Transactional
    public ItemCarrito guardar (ItemCarritoModel model, Carrito carrito) throws WebException{
        ItemCarrito item = new ItemCarrito();
        validarDatos(model);

        item = itemCarritoConverter.modeloToEntidad(model);

        item.setPrecioUnitario(item.getProducto().getPrecio());
        item.setPrecioTotal(item.getPrecioUnitario() * item.getCantidad());
        item.setCarrito(carrito);

        return itemCarritoRepository.save(item);
    }

    public List<ItemCarrito> recorrerItemsModel(List<ItemCarritoModel> model, Carrito carrito) throws WebException{
        List<ItemCarrito> items = new ArrayList<>();
        for (ItemCarritoModel itemModel : model) {
            ItemCarrito itemNuevo = guardar(itemModel, carrito);
            items.add(itemNuevo);
        }
        return items;
    }
}
