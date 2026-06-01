package com.hotel.reservas.service;

import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.model.ReservaServicio;
import com.hotel.reservas.model.Servicio;
import com.hotel.reservas.model.enums.EstadoReserva;
import com.hotel.reservas.repository.ReservaRepository;
import com.hotel.reservas.repository.ReservaServicioRepository;
import com.hotel.reservas.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// MODULARIDAD: ReservaServicioService gestiona exclusivamente la asociación reserva-servicio.
// ENCAPSULAMIENTO: La validación del estado de la reserva se centraliza aquí antes de agregar servicios.
@Service
public class ReservaServicioService {

    @Autowired
    private ReservaServicioRepository reservaServicioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public List<ReservaServicio> listarPorReserva(Long idReserva) {
        return reservaServicioRepository.findByReserva_IdReserva(idReserva);
    }

    public ReservaServicio agregar(ReservaServicio reservaServicio) {
        Reserva reserva = reservaRepository
                .findById(reservaServicio.getReserva().getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!EstadoReserva.CONFIRMADA.equals(reserva.getEstado())
                && !EstadoReserva.CHECKIN.equals(reserva.getEstado())) {
            throw new RuntimeException("Solo se pueden agregar servicios a reservas CONFIRMADA o CHECKIN");
        }

        Servicio servicio = servicioRepository
                .findById(reservaServicio.getServicio().getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        int cantidad = reservaServicio.getCantidad() != null ? reservaServicio.getCantidad() : 1;
        reservaServicio.setSubtotal(servicio.getPrecio() * cantidad);
        reservaServicio.setServicio(servicio);
        reservaServicio.setReserva(reserva);

        return reservaServicioRepository.save(reservaServicio);
    }
}
