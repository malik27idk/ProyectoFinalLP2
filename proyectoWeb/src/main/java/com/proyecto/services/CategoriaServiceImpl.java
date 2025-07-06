package com.proyecto.services;

import com.proyecto.models.Categoria;
import com.proyecto.repositories.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private ICategoriaRepository repo;

    @Override
    public List<Categoria> listarTodas() {
        return repo.findAll();
    }
}
