package com.carrito.web.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carrito.web.entidades.Carrito;
import com.carrito.web.entidades.ItemCarrito;
import com.carrito.web.entidades.Producto;
import com.carrito.web.entidades.Usuario;
import com.carrito.web.enumeraciones.Rol;
import com.carrito.web.enumeraciones.TipoCarrito;
import com.carrito.web.excepciones.WebException;
import com.carrito.web.modelos.CarritoModel;
import com.carrito.web.modelos.ItemCarritoModel;
import com.carrito.web.repositorios.CarritoRepository;

@Service
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ItemCarritoService itemCarritoService;

    @Autowired
    private FechasPromocionalesService fechasPromocionalesService;

    @Autowired
    private UsuarioService usuarioService;

    public CarritoModel agregarItem(ItemCarritoModel item, CarritoModel carritoModel, LocalDate fechaActual, Usuario usuario) throws WebException{
        itemCarritoService.validarDatos(item);

        if(carritoModel.getItems().size() != 0 && debeModificar(carritoModel, item)){
            carritoModel = modificarItem(carritoModel, item);
        }else{
            carritoModel.getItems().add(item);
        }


        if(fechaActual == null){
            throw new WebException("Debe ingresar una fecha al sistema primero.");
        }

        if(usuario == null){
            throw new WebException("Debe haber un usuario logueado");
        }

        usuario = usuarioService.verificarRol(usuario.getId(), fechaActual);

        if(carritoModel.getFecha() == null || carritoModel.getFecha().isEmpty()){
            carritoModel.setFecha(convertirFechaAString(fechaActual));
        }

        if(carritoModel.getTipoCarrito() == null){
            if(fechasPromocionalesService.hayPromocion(fechaActual)){
                carritoModel.setTipoCarrito(TipoCarrito.PROMOCIONAL);
            }else{
                if(usuario.getRol().equals(Rol.VIP)){
                    carritoModel.setTipoCarrito(TipoCarrito.VIP);
                }else{
                    carritoModel.setTipoCarrito(TipoCarrito.NORMAL);
                }
            }
        }

        carritoModel.setDescuento(calcularDescuento(carritoModel));

        
        carritoModel.setTotalParcial(calcularPrecioParcial(carritoModel));

        carritoModel.setTotal(carritoModel.getTotalParcial() - carritoModel.getDescuento());
        carritoModel.setItems(nombrarItems(carritoModel.getItems()));
        return carritoModel;
    }

    public CarritoModel quitarItem(CarritoModel model, ItemCarritoModel item) throws WebException{
        if(model == null){
            throw new WebException("No existe carrito en la session.");
        }

        if(item == null){
            throw new WebException("Debe sellecionar un item a quitar.");
        }

        if(!model.getItems().contains(item)){
            throw new WebException("El item seleccionado no esta en el carrito actual");
        }else{
            List<ItemCarritoModel> items = model.getItems();
            items.remove(item);
            model.setItems(items);
        }

        return model;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
    public Carrito comprar(CarritoModel model, Usuario usuario) throws WebException{
        List<ItemCarrito> items = new ArrayList<>();
        validarDatos(model);

        Carrito carrito = new Carrito();
        carrito = carritoRepository.save(carrito);

        items = convertirItems(model.getItems(), carrito);

        LocalDate fechaActual = convertirFecha(model.getFecha());

        carrito.setFechaCompra(fechaActual);
        carrito.setUsuario(usuario);
        carrito.setItems(items);
        carrito.setTipoCarrito(model.getTipoCarrito());
        carrito.setDescuento(model.getDescuento());
        carrito.setTotalParcial(model.getTotalParcial());
        carrito.setTotal(model.getTotal());

        return carritoRepository.save(carrito);
    }

    public void validarDatos(CarritoModel model) throws WebException{
        if(model.getItems().isEmpty() || model.getItems().size() == 0){
            throw new WebException("Debe tener items para comprar el carrito");
        }

        if(model.getFecha() == null || model.getFecha().isEmpty()){
            throw new WebException("El carrito debe tener unna echa de compra.");
        }

        if(model.getTipoCarrito() == null){
            throw new WebException("El carrito debe tener un tipo definido");
        }

        if(model.getTotalParcial() <= 0){
            throw new WebException("El carrito debe tener un total parcial");
        }

        if(model.getTotal() <= 0){
            throw new WebException("El carrito debe tener un total");
        }
    }

    public List<ItemCarrito> convertirItems(List<ItemCarritoModel> listaItems, Carrito carrito) throws WebException{
        return itemCarritoService.recorrerItemsModel(listaItems, carrito);
    }

    public LocalDate convertirFecha(String cadena) throws WebException{
        return fechasPromocionalesService.convertirAfecha(cadena);
    }

    public String convertirFechaAString(LocalDate fecha) throws WebException{
        return fechasPromocionalesService.convertirAString(fecha);
    }

    public int cantidadProductos(List<ItemCarritoModel> items){
        int cantidad = 0;

        for (ItemCarritoModel item : items) {
            cantidad += item.getCantidad();
        }

        return cantidad;
    }

    public double calcularDescuento(CarritoModel carritoModel) {
        int cantidadProductos = cantidadProductos(carritoModel.getItems());
        double totalParcial = carritoModel.getTotalParcial();
    
        if (cantidadProductos == 4) {
            return totalParcial * 0.25;
        }
    
        double descuento = 0;
        TipoCarrito tipoCarrito = carritoModel.getTipoCarrito();
    
        if (cantidadProductos > 10) {
            switch (tipoCarrito) {
                case PROMOCIONAL:
                    descuento = 300;
                    break;
                case VIP:
                    Producto producto = productoService.buscarPorId(carritoModel.getItems().get(0).getIdProducto());
                    descuento = producto.getPrecio() + 500;
                    break;
                case NORMAL:
                    descuento = 100;
                    break;
                default:
                    break;
            }
        }
    
        return descuento;
    }

    public List<ItemCarritoModel> obtenerItemsOrdenadosPorPrecio(List<ItemCarritoModel> items) {
        Map<String, Double> precios = items.stream()
                .collect(Collectors.toMap(
                    ItemCarritoModel::getIdProducto,
                    item -> productoService.buscarPorId(item.getIdProducto()).getPrecio()
                ));

        return items.stream()
                .sorted(Comparator.comparingDouble(item -> precios.get(item.getIdProducto())))
                .collect(Collectors.toList());
    }

    public CarritoModel modificarItem(CarritoModel carritoModel, ItemCarritoModel itemModel){
        List<ItemCarritoModel> items = carritoModel.getItems();

        for (ItemCarritoModel item : items) {
            if(item.getIdProducto().equals(itemModel.getIdProducto())){
                item.setCantidad(itemModel.getCantidad());
            }
        }
        carritoModel.setItems(items);
        return carritoModel;
    }

    public boolean debeModificar(CarritoModel carritoModel, ItemCarritoModel itemModel){
        return carritoModel.getItems().stream()
                    .anyMatch(item -> item.getIdProducto().equals(itemModel.getIdProducto()));
    }

    public double calcularPrecioParcial(CarritoModel model){
        double precioParcial = 0;

        for (ItemCarritoModel item : model.getItems()) {
            Producto producto = productoService.buscarPorId(item.getIdProducto());
            precioParcial += producto.getPrecio() * item.getCantidad();
        }

        return precioParcial;
    }

    public int contarItems(CarritoModel carritoModel){
        return carritoModel.getItems().size();
    }

    public List<ItemCarritoModel> nombrarItems(List<ItemCarritoModel> model){
        for (ItemCarritoModel itemCarritoModel : model) {
            Producto producto = productoService.buscarPorId(itemCarritoModel.getIdProducto());
            itemCarritoModel.setNombre(producto.getNombre());
        }
        return model;
    }

    public List<CarritoModel> misCarritos(Usuario usuario) throws WebException{
        List<Carrito> carros = carritoRepository.misCarritos(usuario.getId());
        List<CarritoModel> carritoModels = new ArrayList<>();

        for (Carrito carrito : carros) {
            CarritoModel model = new CarritoModel();
            BeanUtils.copyProperties(carrito, model);
            model.setFecha(convertirFechaAString(carrito.getFechaCompra()));
            List<ItemCarritoModel> items = new ArrayList<>();
            for (ItemCarrito item : carrito.getItems()) {
                ItemCarritoModel itemModel = new ItemCarritoModel();
                itemModel = itemCarritoService.convertirModel(item);
                items.add(itemModel);
            }
            model.setItems(items);
            carritoModels.add(model);
        }

        return carritoModels;
    }

}
