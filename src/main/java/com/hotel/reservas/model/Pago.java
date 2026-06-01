package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.MetodoPago;
import jakarta.persistence.*;

import java.time.LocalDateTime;

// ENCAPSULAMIENTO: La información del pago (monto, método, fecha) está protegida
// mediante atributos privados con acceso a través de getters y setters.
@Entity
@Table(name = "pago")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private Double monto;
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    public Pago() {}

    public Pago(Double monto, LocalDateTime fecha, MetodoPago metodoPago, Factura factura) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.factura = factura;
    }

    public Long getIdPago() { return idPago; }
    public void setIdPago(Long idPago) { this.idPago = idPago; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
}
