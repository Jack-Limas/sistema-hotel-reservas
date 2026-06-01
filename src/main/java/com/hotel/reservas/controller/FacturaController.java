package com.hotel.reservas.controller;

import com.hotel.reservas.model.Pago;
import com.hotel.reservas.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// MODULARIDAD: FacturaController solo enruta peticiones HTTP a FacturaService.
@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facturaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/generar/{idReserva}")
    public ResponseEntity<?> generar(@PathVariable Long idReserva) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.generarFactura(idReserva));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Error: " + e.getMessage()
                    + " — Verifica que la reserva #" + idReserva
                    + " esté en estado CHECKOUT y no tenga factura previa");
        }
    }

    @PostMapping("/{id}/pago")
    public ResponseEntity<?> registrarPago(@PathVariable Long id, @RequestBody Pago pago) {
        try {
            return ResponseEntity.ok(facturaService.registrarPago(id, pago));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
