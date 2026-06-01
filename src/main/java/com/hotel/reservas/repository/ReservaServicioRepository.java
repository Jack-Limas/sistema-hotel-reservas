package com.hotel.reservas.repository;

import com.hotel.reservas.model.ReservaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaServicioRepository extends JpaRepository<ReservaServicio, Long> {
    List<ReservaServicio> findByReserva_IdReserva(Long idReserva);
}
