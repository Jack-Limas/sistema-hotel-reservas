package com.hotel.reservas.repository;

import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.model.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByHuesped_IdHuesped(Long idHuesped);
    List<Reserva> findByEstado(EstadoReserva estado);
    List<Reserva> findByHabitacion_IdHabitacionAndEstadoNot(Long idHabitacion, EstadoReserva estado);
}
