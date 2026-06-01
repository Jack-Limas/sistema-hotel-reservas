package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.TipoHabitacion;
import jakarta.persistence.*;

// HERENCIA: Extiende Habitacion con atributos propios de la habitación ejecutiva.
// POLIMORFISMO: Implementa calcularPrecio() sumando cargos por desayuno y piso ejecutivo.
@Entity
@Table(name = "habitacion_ejecutiva")
@PrimaryKeyJoinColumn
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HabitacionEjecutiva extends Habitacion {

    private Double precioBase;
    private Boolean incluyeDesayuno;
    private Boolean pisoEjecutivo;

    public HabitacionEjecutiva() {}

    public HabitacionEjecutiva(String numero, Integer piso, Integer capacidad,
                                String descripcion, Boolean disponible, TipoHabitacion tipo,
                                Double precioBase, Boolean incluyeDesayuno, Boolean pisoEjecutivo) {
        super(numero, piso, capacidad, descripcion, disponible, tipo);
        this.precioBase = precioBase;
        this.incluyeDesayuno = incluyeDesayuno;
        this.pisoEjecutivo = pisoEjecutivo;
    }

    // POLIMORFISMO: Suma cargo por desayuno (+50000) y/o piso ejecutivo (+60000) sobre el precio base.
    @Override
    public Double calcularPrecio() {
        double precio = precioBase;
        if (Boolean.TRUE.equals(incluyeDesayuno)) precio += 50000;
        if (Boolean.TRUE.equals(pisoEjecutivo)) precio += 60000;
        return precio;
    }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public Boolean getIncluyeDesayuno() { return incluyeDesayuno; }
    public void setIncluyeDesayuno(Boolean incluyeDesayuno) { this.incluyeDesayuno = incluyeDesayuno; }

    public Boolean getPisoEjecutivo() { return pisoEjecutivo; }
    public void setPisoEjecutivo(Boolean pisoEjecutivo) { this.pisoEjecutivo = pisoEjecutivo; }
}
