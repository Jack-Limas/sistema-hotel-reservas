package com.hotel.reservas.repository;

import com.hotel.reservas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByReserva_IdReserva(Long idReserva);
}
