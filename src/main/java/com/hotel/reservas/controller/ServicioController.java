package com.hotel.reservas.controller;

import com.hotel.reservas.model.Servicio;
import com.hotel.reservas.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MODULARIDAD: ServicioController solo enruta peticiones HTTP a ServicioService.
@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<Servicio>> listar() {
        return ResponseEntity.ok(servicioService.listar());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Servicio>> listarActivos() {
        return ResponseEntity.ok(servicioService.listarActivos());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Servicio servicio) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.guardar(servicio));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
