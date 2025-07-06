package com.proyecto.services;

import com.proyecto.models.Producto;
import com.proyecto.repositories.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoRepository repo;

    @Override
    public List<Producto> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Producto> buscarPorCategoria(Integer idCategoria) {
        return repo.findByCategoria_Id(idCategoria);
    }

    @Override
    public List<Producto> buscarPorMarca(Integer idMarca) {
        return repo.findByMarca_Id(idMarca);
    }

    @Override
    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // ✅ MÉTODO NUEVO PARA FILTROS
    @Override
    public List<Producto> filtrarProductos(String nombre, Integer idCategoria, Integer idMarca) {
        String nombreLike = (nombre != null && !nombre.isBlank()) ? "%" + nombre + "%" : null;
        return repo.buscarPorFiltros(nombreLike, idCategoria, idMarca);
    }
}
