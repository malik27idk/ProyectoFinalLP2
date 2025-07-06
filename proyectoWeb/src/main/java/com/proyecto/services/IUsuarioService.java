package com.proyecto.services;

import com.proyecto.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarTodos();
    Optional<Usuario> buscarPorId(Integer id);
    Optional<Usuario> buscarPorCorreo(String correo);
    Usuario guardar(Usuario usuario);
    void eliminar(Integer id);

    // ✅ Agregado para "Mi cuenta"
    Usuario findByCorreo(String correo);
}
