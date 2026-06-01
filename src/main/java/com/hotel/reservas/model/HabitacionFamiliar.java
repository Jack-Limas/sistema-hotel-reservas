package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.TipoHabitacion;
import jakarta.persistence.*;

// HERENCIA — hereda de Habitacion: idHabitacion, numero, piso,
//            capacidad, descripcion, disponible, tipo
// POLIMORFISMO — sobreescribe calcularPrecio() con lógica propia
// ENCAPSULAMIENTO — atributos propios privados con getters/setters
@Entity
@Table(name = "habitacion_familiar")
@PrimaryKeyJoinColumn
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HabitacionFamiliar extends Habitacion {

    private Double precioBase;
    private Integer numeroCamas;

    public HabitacionFamiliar() {}

    public HabitacionFamiliar(String numero, Integer piso, Integer capacidad,
                               String descripcion, Boolean disponible,
                               TipoHabitacion tipo, Double precioBase, Integer numeroCamas) {
        super(numero, piso, capacidad, descripcion, disponible, tipo);
        this.precioBase = precioBase;
        this.numeroCamas = numeroCamas;
    }

    // POLIMORFISMO: El precio familiar multiplica el precio base por el número de camas con un 10% de descuento.
    @Override
    public Double calcularPrecio() {
        return precioBase * numeroCamas * 0.9;
    }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public Integer getNumeroCamas() { return numeroCamas; }
    public void setNumeroCamas(Integer numeroCamas) { this.numeroCamas = numeroCamas; }
}
