package com.proyecto.services;

import com.proyecto.models.Cupon;
import com.proyecto.repositories.ICuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CuponServiceImpl implements ICuponService {

    @Autowired
    private ICuponRepository cuponRepo;

    @Override
    public Optional<Cupon> buscarPorCodigo(String codigo) {
        return cuponRepo.findByCodigo(codigo);
    }

    @Override
    public boolean validarCupon(Cupon cupon, BigDecimal montoTotal) {
        if (!"1".equals(cupon.getEstado())) return false;
        LocalDate hoy = LocalDate.now();

        boolean dentroDeFechas = 
            (hoy.isEqual(cupon.getFechaInicio()) || hoy.isAfter(cupon.getFechaInicio())) &&
            (hoy.isEqual(cupon.getFechaFin()) || hoy.isBefore(cupon.getFechaFin()));

        boolean cumpleMonto = cupon.getMontoMinimo() == null || montoTotal.compareTo(cupon.getMontoMinimo()) >= 0;

        return dentroDeFechas && cumpleMonto;
    }

    @Override
    public List<Cupon> listarTodos() {
        return cuponRepo.findAll();
    }

    @Override
    public Cupon guardar(Cupon cupon) {
        return cuponRepo.save(cupon);
    }

    @Override
    public void eliminar(Integer id) {
        cuponRepo.deleteById(id);
    }
}

