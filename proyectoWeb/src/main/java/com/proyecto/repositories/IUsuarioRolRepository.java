package com.proyecto.repositories;

import com.proyecto.models.UsuarioRol;
import com.proyecto.models.UsuarioRolId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> { 

    List<UsuarioRol> findByUsuario_Id(Integer idUsuario);
}
