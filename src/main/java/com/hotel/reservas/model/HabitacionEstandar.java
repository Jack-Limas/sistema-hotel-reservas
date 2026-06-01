package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.TipoHabitacion;
import jakarta.persistence.*;

// HERENCIA: Extiende Habitacion heredando numero, piso, capacidad, descripcion, disponible y tipo.
// POLIMORFISMO: Sobrescribe calcularPrecio() retornando directamente el precio base.
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
