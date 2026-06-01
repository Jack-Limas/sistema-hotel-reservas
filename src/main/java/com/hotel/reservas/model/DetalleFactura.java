package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

// ENCAPSULAMIENTO: Cada línea de detalle de factura protege sus valores de precio
// y cantidad mediante atributos privados con acceso controlado.
@Entity
@Table(name = "detalle_factura")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    public DetalleFactura() {}

    public DetalleFactura(String descripcion, Integer cantidad, Double precioUnitario,
                           Double subtotal, Factura factura) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.factura = factura;
    }

    public Long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Long idDetalle) { this.idDetalle = idDetalle; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
}
