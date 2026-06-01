package com.hotel.reservas.repository;

import com.hotel.reservas.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    Optional<Huesped> findByCorreo(String correo);
    Optional<Huesped> findByNumeroDocumento(String numeroDocumento);
    List<Huesped> findByActivoTrue();
}
