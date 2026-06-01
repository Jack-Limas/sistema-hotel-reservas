package com.hotel.reservas.service;

import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.model.enums.EstadoReserva;
import com.hotel.reservas.repository.HabitacionRepository;
import com.hotel.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// MODULARIDAD: ReservaService gestiona el ciclo de vida completo de las reservas.
// ENCAPSULAMIENTO: Los cambios de estado de reservas y habitaciones ocurren solo aquí.
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public List<Reserva> listarPorHuesped(Long idHuesped) {
        return reservaRepository.findByHuesped_IdHuesped(idHuesped);
    }

    public Reserva crear(Reserva reserva) {
        Habitacion habitacion = habitacionRepository
                .findById(reserva.getHabitacion().getIdHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitacion no encontrada"));

        if (!Boolean.TRUE.equals(habitacion.getDisponible())) {
            throw new RuntimeException("Habitacion no disponible");
        }

        // POLIMORFISMO: calcularPrecio() se comporta diferente según el tipo concreto de habitación.
        // Java resuelve en tiempo de ejecución si es HabitacionEstandar, HabitacionFamiliar,
        // HabitacionSuite o HabitacionEjecutiva, invocando la implementación correcta de cada una.
        double precioPorNoche = habitacion.calcularPrecio();
        int noches = reserva.calcularNoches();
        reserva.setTotalEstimado(precioPorNoche * noches);
        reserva.setHabitacion(habitacion);

        habitacion.setDisponible(false);
        habitacionRepository.save(habitacion);

        return reservaRepository.save(reserva);
    }

    public Reserva hacerCheckin(Long idReserva) {
        Reserva reserva = obtenerPorId(idReserva);
        if (!EstadoReserva.PENDIENTE.equals(reserva.getEstado())
                && !EstadoReserva.CONFIRMADA.equals(reserva.getEstado())) {
            throw new RuntimeException("La reserva debe estar en estado PENDIENTE o CONFIRMADA para hacer check-in");
        }
        reserva.setEstado(EstadoReserva.CHECKIN);
        return reservaRepository.save(reserva);
    }

    public Reserva hacerCheckout(Long idReserva) {
        Reserva reserva = obtenerPorId(idReserva);
        if (!EstadoReserva.CHECKIN.equals(reserva.getEstado())) {
            throw new RuntimeException("La reserva debe estar en estado CHECKIN para hacer check-out");
        }
        reserva.setEstado(EstadoReserva.CHECKOUT);
        Habitacion habitacion = reserva.getHabitacion();
        habitacion.setDisponible(true);
        habitacionRepository.save(habitacion);
        return reservaRepository.save(reserva);
    }

    public void cancelar(Long idReserva) {
        Reserva reserva = obtenerPorId(idReserva);
        if (!EstadoReserva.PENDIENTE.equals(reserva.getEstado())
                && !EstadoReserva.CONFIRMADA.equals(reserva.getEstado())) {
            throw new RuntimeException("Solo se pueden cancelar reservas en estado PENDIENTE o CONFIRMADA");
        }
        reserva.setEstado(EstadoReserva.CANCELADA);
        Habitacion habitacion = reserva.getHabitacion();
        habitacion.setDisponible(true);
        habitacionRepository.save(habitacion);
        reservaRepository.save(reserva);
    }
}
