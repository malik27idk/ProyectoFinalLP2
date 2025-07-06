package com.proyecto.services;
import com.proyecto.models.*;
import com.proyecto.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoServiceImpl implements ICarritoService  {

    @Autowired
    private ICarritoRepository carritoRepo;

    @Autowired
    private ICarritoItemRepository itemRepo;

    @Autowired
    private IProductoRepository productoRepo;

    @Autowired
    private ITallaRepository tallaRepo;

    @Override
    public Carrito obtenerOCrearCarritoPorUsuario(Integer idUsuario) {
        Carrito carrito = carritoRepo.findByUsuario_Id(idUsuario);
        if (carrito == null) {
            carrito = new Carrito();
            Usuario usuario = new Usuario();
            usuario.setId(idUsuario);
            carrito.setUsuario(usuario);
            carrito = carritoRepo.save(carrito);
        }
        return carrito;
    }

    @Override
    public List<CarritoItem> listarItems(Integer idCarrito) {
        return itemRepo.findByCarrito_Id(idCarrito);
    }

    @Override
    public void agregarProducto(Integer idCarrito, Integer idProducto, Integer idTalla, int cantidad) {
        CarritoItem item = new CarritoItem();
        Carrito carrito = new Carrito();
        carrito.setId(idCarrito);

        Producto producto = new Producto();
        producto.setId(idProducto);

        Talla talla = new Talla();
        talla.setId(idTalla);

        CarritoItemId itemId = new CarritoItemId(idCarrito, idProducto, idTalla);
        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setTalla(talla);
        item.setCantidad(cantidad);

        itemRepo.save(item);
    }

    @Override
    public void eliminarItem(Integer idCarrito, Integer idProducto, Integer idTalla) {
        CarritoItemId id = new CarritoItemId(idCarrito, idProducto, idTalla);
        itemRepo.deleteById(id);
    }

    @Override
    public void vaciarCarrito(Integer idCarrito) {
        List<CarritoItem> items = itemRepo.findByCarrito_Id(idCarrito);
        itemRepo.deleteAll(items);
    }

}
