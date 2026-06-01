package com.hotel.reservas.service;

import com.hotel.reservas.model.DetalleFactura;
import com.hotel.reservas.model.Factura;
import com.hotel.reservas.model.Habitacion;
import com.hotel.reservas.model.Pago;
import com.hotel.reservas.model.Reserva;
import com.hotel.reservas.model.ReservaServicio;
import com.hotel.reservas.model.enums.EstadoFactura;
import com.hotel.reservas.model.enums.EstadoReserva;
import com.hotel.reservas.repository.DetalleFacturaRepository;
import com.hotel.reservas.repository.FacturaRepository;
import com.hotel.reservas.repository.PagoRepository;
import com.hotel.reservas.repository.ReservaRepository;
import com.hotel.reservas.repository.ReservaServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// MODULARIDAD: FacturaService centraliza la lógica de facturación del sistema.
// ENCAPSULAMIENTO: El estado de la factura solo cambia a través de este servicio.
@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private ReservaServicioRepository reservaServicioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    public Factura generarFactura(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!EstadoReserva.CHECKOUT.equals(reserva.getEstado())) {
            throw new RuntimeException("Solo se puede facturar una reserva en estado CHECKOUT");
        }

        if (facturaRepository.findByReserva_IdReserva(idReserva).isPresent()) {
            throw new RuntimeException("Ya existe una factura para esta reserva");
        }

        Factura factura = new Factura(reserva);
        factura = facturaRepository.save(factura);

        List<DetalleFactura> detalles = new ArrayList<>();

        // Detalle de la habitación
        // POLIMORFISMO: calcularPrecio() invoca la implementación del subtipo concreto de habitación.
        Habitacion habitacion = reserva.getHabitacion();
        int noches = reserva.calcularNoches();
        double precioNoche = habitacion.calcularPrecio();
        String descHabitacion = "Habitacion " + habitacion.getNumero() + " - " + noches + " noches";

        DetalleFactura detHabitacion = new DetalleFactura(
                descHabitacion, noches, precioNoche, precioNoche * noches, factura);
        detalleFacturaRepository.save(detHabitacion);
        detalles.add(detHabitacion);

        // Detalles de servicios adicionales
        List<ReservaServicio> servicios = reservaServicioRepository.findByReserva_IdReserva(idReserva);
        for (ReservaServicio rs : servicios) {
            DetalleFactura detServicio = new DetalleFactura(
                    rs.getServicio().getNombre(),
                    rs.getCantidad(),
                    rs.getServicio().getPrecio(),
                    rs.getSubtotal(),
                    factura
            );
            detalleFacturaRepository.save(detServicio);
            detalles.add(detServicio);
        }

        double total = detalles.stream().mapToDouble(DetalleFactura::getSubtotal).sum();
        factura.setTotal(total);
        factura.setDetalles(detalles);
        return facturaRepository.save(factura);
    }

    public Factura registrarPago(Long idFactura, Pago pago) {
        Factura factura = obtenerPorId(idFactura);
        if (!EstadoFactura.PENDIENTE.equals(factura.getEstado())) {
            throw new RuntimeException("La factura no está en estado PENDIENTE");
        }
        pago.setFactura(factura);
        pagoRepository.save(pago);
        factura.setEstado(EstadoFactura.PAGADA);
        return facturaRepository.save(factura);
    }
}
