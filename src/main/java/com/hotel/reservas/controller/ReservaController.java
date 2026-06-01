package com.hotel.reservas.controller;

import com.hotel.reservas.dto.ReservaRequest;
import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.model.Huesped;
import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.repository.HabitacionRepository;
import com.hotel.reservas.repository.HuespedRepository;
import com.hotel.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MODULARIDAD: ReservaController solo enruta peticiones HTTP a ReservaService.
@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/huesped/{idHuesped}")
    public ResponseEntity<List<Reserva>> listarPorHuesped(@PathVariable Long idHuesped) {
        return ResponseEntity.ok(reservaService.listarPorHuesped(idHuesped));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ReservaRequest request) {
        try {
            // ENCAPSULAMIENTO — el DTO evita exponer la entidad abstracta Habitacion
            // directamente al frontend; el controlador resuelve las referencias por ID.
            Reserva reserva = new Reserva();
            reserva.setFechaInicio(java.time.LocalDate.parse(request.getFechaInicio()));
            reserva.setFechaFin(java.time.LocalDate.parse(request.getFechaFin()));

            Huesped huesped = huespedRepository.findById(request.getIdHuesped())
                    .orElseThrow(() -> new RuntimeException("Huesped no encontrado"));
            reserva.setHuesped(huesped);

            Habitacion habitacion = habitacionRepository.findById(request.getIdHabitacion())
                    .orElseThrow(() -> new RuntimeException("Habitacion no encontrada"));
            reserva.setHabitacion(habitacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(reserva));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/checkin")
    public ResponseEntity<?> hacerCheckin(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservaService.hacerCheckin(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<?> hacerCheckout(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservaService.hacerCheckout(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            reservaService.cancelar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
