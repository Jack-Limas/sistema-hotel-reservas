package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

// ENCAPSULAMIENTO: Relaciona una reserva con un servicio adicional, encapsulando
// la cantidad y el subtotal como atributos privados con acceso controlado.
@Entity
@Table(name = "reserva_servicio")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReservaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservaServicio;

    private Integer cantidad = 1;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    public ReservaServicio() {}

    public ReservaServicio(Reserva reserva, Servicio servicio,
                            Integer cantidad, Double subtotal) {
        this.reserva = reserva;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public Long getIdReservaServicio() { return idReservaServicio; }
    public void setIdReservaServicio(Long idReservaServicio) { this.idReservaServicio = idReservaServicio; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
}
