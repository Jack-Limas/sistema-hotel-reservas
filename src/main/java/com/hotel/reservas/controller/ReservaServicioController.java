package com.hotel.reservas.controller;

import com.hotel.reservas.model.ReservaServicio;
import com.hotel.reservas.service.ReservaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MODULARIDAD: ReservaServicioController solo enruta peticiones HTTP a ReservaServicioService.
@RestController
@RequestMapping("/api/reserva-servicios")
@CrossOrigin(origins = "*")
public class ReservaServicioController {

    @Autowired
    private ReservaServicioService reservaServicioService;

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<ReservaServicio>> listarPorReserva(@PathVariable Long idReserva) {
        return ResponseEntity.ok(reservaServicioService.listarPorReserva(idReserva));
    }

    @PostMapping
    public ResponseEntity<?> agregar(@RequestBody ReservaServicio reservaServicio) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaServicioService.agregar(reservaServicio));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
