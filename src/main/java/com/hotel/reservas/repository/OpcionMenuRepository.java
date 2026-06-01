package com.hotel.reservas.repository;

import com.hotel.reservas.model.OpcionMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionMenuRepository extends JpaRepository<OpcionMenu, Long> {
    List<OpcionMenu> findByRolAndActivoTrue(String rol);
    List<OpcionMenu> findByPadreIsNullAndRolAndActivoTrue(String rol);
}
