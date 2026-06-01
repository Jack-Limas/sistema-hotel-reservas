package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.TipoHabitacion;
import jakarta.persistence.*;

// HERENCIA — hereda de Habitacion: idHabitacion, numero, piso,
//            capacidad, descripcion, disponible, tipo
// POLIMORFISMO — sobreescribe calcularPrecio() con lógica propia
// ENCAPSULAMIENTO — atributos propios privados con getters/setters
@Entity
@Table(name = "habitacion_estandar")
@PrimaryKeyJoinColumn
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HabitacionEstandar extends Habitacion {

    private Double precioBase;

    public HabitacionEstandar() {}

    public HabitacionEstandar(String numero, Integer piso, Integer capacidad,
                               String descripcion, Boolean disponible,
                               TipoHabitacion tipo, Double precioBase) {
        super(numero, piso, capacidad, descripcion, disponible, tipo);
        this.precioBase = precioBase;
    }

    // POLIMORFISMO: El precio de la habitación estándar es directamente el precio base sin cargos adicionales.
    @Override
    public Double calcularPrecio() {
        return precioBase;
    }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }
}
