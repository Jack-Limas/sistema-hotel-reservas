package com.hotel.reservas.controller;

import com.hotel.reservas.dto.HabitacionRequest;
import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.model.HabitacionEjecutiva;
import com.hotel.reservas.model.HabitacionEstandar;
import com.hotel.reservas.model.HabitacionFamiliar;
import com.hotel.reservas.model.HabitacionSuite;
import com.hotel.reservas.service.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MODULARIDAD: HabitacionController solo enruta peticiones HTTP a HabitacionService.
@RestController
@RequestMapping("/api/habitaciones")
@CrossOrigin(origins = "*")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<List<Habitacion>> listar() {
        return ResponseEntity.ok(habitacionService.listar());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Habitacion>> listarDisponibles() {
        return ResponseEntity.ok(habitacionService.listarDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(habitacionService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody HabitacionRequest request) {
        try {
            // POLIMORFISMO — creamos la subclase correcta según el tipo recibido.
            // Mismo método guardar(), diferente objeto concreto según el tipo.
            // Spring/Hibernate persiste la tabla correcta gracias a @Inheritance(JOINED).
            Habitacion habitacion;

            switch (request.getTipo()) {
                case "SUITE" -> {
                    HabitacionSuite s = new HabitacionSuite();
                    s.setPrecioBase(request.getPrecioBase());
                    s.setIncluyeDesayuno(
                            request.getIncluyeDesayuno() != null && request.getIncluyeDesayuno());
                    s.setVistaAlMar(
                            request.getVistaAlMar() != null && request.getVistaAlMar());
                    habitacion = s;
                }
                case "FAMILIAR" -> {
                    HabitacionFamiliar f = new HabitacionFamiliar();
                    f.setPrecioBase(request.getPrecioBase());
                    f.setNumeroCamas(
                            request.getNumeroCamas() != null ? request.getNumeroCamas() : 2);
                    habitacion = f;
                }
                case "EJECUTIVA" -> {
                    HabitacionEjecutiva e = new HabitacionEjecutiva();
                    e.setPrecioBase(request.getPrecioBase());
                    e.setIncluyeDesayuno(
                            request.getIncluyeDesayuno() != null && request.getIncluyeDesayuno());
                    e.setPisoEjecutivo(
                            request.getPisoEjecutivo() != null && request.getPisoEjecutivo());
                    habitacion = e;
                }
                default -> {
                    HabitacionEstandar est = new HabitacionEstandar();
                    est.setPrecioBase(request.getPrecioBase());
                    habitacion = est;
                }
            }

            // Campos comunes heredados de la clase abstracta Habitacion
            habitacion.setNumero(request.getNumero());
            habitacion.setPiso(request.getPiso());
            habitacion.setCapacidad(request.getCapacidad());
            habitacion.setDescripcion(request.getDescripcion());
            habitacion.setDisponible(
                    request.getDisponible() != null ? request.getDisponible() : true);
            habitacion.setTipo(
                    com.hotel.reservas.model.enums.TipoHabitacion.valueOf(request.getTipo()));

            Habitacion nueva = habitacionService.guardar(habitacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        try {
            return ResponseEntity.ok(habitacionService.actualizar(id, habitacion));
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("no encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            habitacionService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
