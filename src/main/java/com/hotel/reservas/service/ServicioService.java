package com.hotel.reservas.service;

import com.hotel.reservas.model.Servicio;
import com.hotel.reservas.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// MODULARIDAD: ServicioService encapsula la gestión de servicios adicionales del hotel.
// ENCAPSULAMIENTO: El estado activo/inactivo de un servicio se controla solo desde aquí.
@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    public List<Servicio> listarActivos() {
        return servicioRepository.findByActivoTrue();
    }

    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
}
