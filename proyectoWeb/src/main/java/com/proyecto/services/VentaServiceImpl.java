package com.proyecto.services;

import com.proyecto.models.*;
import com.proyecto.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements IVentaService {

    @Autowired
    private IVentaRepository ventaRepo;

    @Autowired
    private IDetalleVentaRepository detalleRepo;

    @Autowired
    private ICarritoRepository carritoRepo;

    @Autowired
    private ICarritoItemRepository carritoItemRepo;

    @Autowired
    private IProductoRepository productoRepo;

    @Autowired
    private IProductoTallaStockRepository stockRepo;

    @Autowired
    private IMetodoPagoRepository metodoPagoRepo;

    @Autowired
    private IVentaPagoRepository ventaPagoRepo;

    @Autowired
    private ICuponRepository cuponRepo;

    @Override
    public Venta registrarVenta(Integer idUsuario, Integer idMetodoPago, Optional<String> codigoCupon) {
        Carrito carrito = carritoRepo.findByUsuario_Id(idUsuario);
        if (carrito == null) throw new RuntimeException("El usuario no tiene carrito");

        List<CarritoItem> items = carritoItemRepo.findByCarrito_Id(carrito.getId());
        if (items.isEmpty()) throw new RuntimeException("El carrito está vacío");

        BigDecimal total = BigDecimal.ZERO;
        for (CarritoItem item : items) {
            BigDecimal precio = item.getProducto().getPrecio();
            BigDecimal descuento = item.getProducto().getDescuento();
            BigDecimal precioFinal = precio.subtract(precio.multiply(descuento).divide(BigDecimal.valueOf(100)));
            total = total.add(precioFinal.multiply(BigDecimal.valueOf(item.getCantidad())));
        }

        Cupon cupon = null;
        if (codigoCupon.isPresent() && !codigoCupon.get().isBlank()) {
            cupon = cuponRepo.findByCodigo(codigoCupon.get())
                    .filter(c -> "1".equals(c.getEstado()))
                    .filter(c -> LocalDate.now().isAfter(c.getFechaInicio().minusDays(1)))
                    .filter(c -> LocalDate.now().isBefore(c.getFechaFin().plusDays(1)))
                    .orElseThrow(() -> new RuntimeException("Cupón inválido o vencido"));

            if (cupon.getMontoMinimo() == null || total.compareTo(cupon.getMontoMinimo()) >= 0) {
                if ("P".equals(cupon.getTipo())) {
                    total = total.subtract(total.multiply(cupon.getValor()).divide(BigDecimal.valueOf(100)));
                } else if ("M".equals(cupon.getTipo())) {
                    total = total.subtract(cupon.getValor());
                }
            }
        }

        Venta venta = new Venta();
        venta.setUsuario(new Usuario(idUsuario, null, null, null, null, null, null, null, null));
        venta.setFecha(LocalDate.now());
        venta.setTotal(total);
        venta.setEstado("1");
        venta.setCupon(cupon);
        venta = ventaRepo.save(venta);

        for (CarritoItem item : items) {
            Producto producto = productoRepo.findById(item.getProducto().getId()).orElseThrow();
            BigDecimal precioFinal = producto.getPrecio()
                    .subtract(producto.getPrecio().multiply(producto.getDescuento()).divide(BigDecimal.valueOf(100)));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setTalla(item.getTalla());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(precioFinal);
            detalleRepo.save(detalle);

            ProductoTallaStock pts = stockRepo.findById(
                    new ProductoTallaStockId(producto.getId(), item.getTalla().getId()))
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado"));

            int nuevoStock = pts.getStock() - item.getCantidad();
            if (nuevoStock < 0) throw new RuntimeException("Stock insuficiente");
            pts.setStock(nuevoStock);
            stockRepo.save(pts);
        }

        MetodoPago metodo = metodoPagoRepo.findById(idMetodoPago)
                .orElseThrow(() -> new RuntimeException("Método de pago inválido"));

        VentaPago vp = new VentaPago();
        vp.setVenta(venta);
        vp.setMetodoPago(metodo);
        ventaPagoRepo.save(vp);

        carritoItemRepo.deleteAll(items);

        return venta;
    }

    @Override
    public List<Venta> listarTodas() {
        return ventaRepo.findAll();
    }

    @Override
    public Optional<Venta> buscarPorId(Integer id) {
        return ventaRepo.findById(id);
    }

    @Override
    public List<Venta> filtrarVentas(String nombreCliente, String fecha) {
        if ((nombreCliente == null || nombreCliente.isBlank()) && (fecha == null || fecha.isBlank())) {
            return ventaRepo.findAll();
        }

        return ventaRepo.buscarPorFiltros(
                (nombreCliente == null || nombreCliente.isBlank()) ? null : "%" + nombreCliente.toLowerCase() + "%",
                (fecha == null || fecha.isBlank()) ? null : fecha
        );
    }
}
