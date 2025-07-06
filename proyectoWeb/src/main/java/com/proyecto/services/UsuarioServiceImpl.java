package com.proyecto.services;

import com.proyecto.models.Usuario;
import com.proyecto.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository repo;

    @Override
    public List<Usuario> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return repo.findByCorreo(correo);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    // ✅ Método agregado para uso directo en el controlador "Mi cuenta"
    @Override
    public Usuario findByCorreo(String correo) {
        return repo.findByCorreo(correo).orElse(null);
    }
}
