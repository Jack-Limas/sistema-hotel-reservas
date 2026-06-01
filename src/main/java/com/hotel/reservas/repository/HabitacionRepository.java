package com.hotel.reservas.repository;

import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.model.enums.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByDisponibleTrue();
    List<Habitacion> findByTipo(TipoHabitacion tipo);
}
