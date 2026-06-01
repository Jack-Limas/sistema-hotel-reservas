package com.hotel.reservas.service;

import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// MODULARIDAD: HabitacionService gestiona exclusivamente la lógica de habitaciones.
// ENCAPSULAMIENTO: La disponibilidad de habitaciones se controla solo a través de este servicio.
@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    public List<Habitacion> listar() {
        return habitacionRepository.findAll();
    }

    public List<Habitacion> listarDisponibles() {
        return habitacionRepository.findByDisponibleTrue();
    }

    public Habitacion obtenerPorId(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitacion no encontrada"));
    }

    public Habitacion guardar(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    // ENCAPSULAMIENTO: Solo actualiza campos de la clase base (Habitacion abstracta).
    // Los atributos específicos de cada subtipo se gestionan al crear la habitación.
    public Habitacion actualizar(Long id, Habitacion habitacion) {
        Habitacion existente = obtenerPorId(id);
        existente.setNumero(habitacion.getNumero());
        existente.setPiso(habitacion.getPiso());
        existente.setCapacidad(habitacion.getCapacidad());
        existente.setDescripcion(habitacion.getDescripcion());
        existente.setDisponible(habitacion.getDisponible());
        existente.setTipo(habitacion.getTipo());
        return habitacionRepository.save(existente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        habitacionRepository.deleteById(id);
    }

    public void cambiarDisponibilidad(Long id, boolean disponible) {
        Habitacion habitacion = obtenerPorId(id);
        habitacion.setDisponible(disponible);
        habitacionRepository.save(habitacion);
    }
}
