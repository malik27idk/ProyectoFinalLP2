package com.proyecto.services;

import com.proyecto.models.Marca;
import com.proyecto.repositories.IMarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl implements IMarcaService {

    @Autowired
    private IMarcaRepository repo;

    @Override
    public List<Marca> listarTodas() {
        return repo.findAll();
    }
}
