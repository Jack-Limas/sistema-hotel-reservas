package com.hotel.reservas.service;

import com.hotel.reservas.model.Huesped;
import com.hotel.reservas.repository.HuespedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// MODULARIDAD: HuespedService concentra toda la lógica de negocio para huéspedes,
// manteniendo los controladores libres de lógica.
// ENCAPSULAMIENTO: Los datos del huésped se validan y modifican solo a través de este servicio.
@Service
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

    public List<Huesped> listar() {
        return huespedRepository.findAll();
    }

    public Huesped obtenerPorId(Long id) {
        return huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huesped no encontrado"));
    }

    public Huesped guardar(Huesped huesped) {
        return huespedRepository.save(huesped);
    }

    public Huesped actualizar(Long id, Huesped huesped) {
        Huesped existente = obtenerPorId(id);
        existente.setNombre(huesped.getNombre());
        existente.setCorreo(huesped.getCorreo());
        existente.setTelefono(huesped.getTelefono());
        existente.setTipoDocumento(huesped.getTipoDocumento());
        existente.setNumeroDocumento(huesped.getNumeroDocumento());
        existente.setActivo(huesped.getActivo());
        return huespedRepository.save(existente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        huespedRepository.deleteById(id);
    }

    public Huesped buscarPorCorreo(String correo) {
        return huespedRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Huesped no encontrado con correo: " + correo));
    }
}
