package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.TipoHabitacion;
import jakarta.persistence.*;

// HERENCIA: Extiende Habitacion con atributos exclusivos de suite (desayuno y vista al mar).
// POLIMORFISMO: Implementa calcularPrecio() sumando cargos adicionales según las opciones activadas.
@Entity
@Table(name = "habitacion_suite")
@PrimaryKeyJoinColumn
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HabitacionSuite extends Habitacion {

    private Double precioBase;
    private Boolean incluyeDesayuno;
    private Boolean vistaAlMar;

    public HabitacionSuite() {}

    public HabitacionSuite(String numero, Integer piso, Integer capacidad,
                            String descripcion, Boolean disponible, TipoHabitacion tipo,
                            Double precioBase, Boolean incluyeDesayuno, Boolean vistaAlMar) {
        super(numero, piso, capacidad, descripcion, disponible, tipo);
        this.precioBase = precioBase;
        this.incluyeDesayuno = incluyeDesayuno;
        this.vistaAlMar = vistaAlMar;
    }

    // POLIMORFISMO: Agrega cargo por desayuno (+50000) y/o vista al mar (+80000) sobre el precio base.
    @Override
    public Double calcularPrecio() {
        double precio = precioBase;
        if (Boolean.TRUE.equals(incluyeDesayuno)) precio += 50000;
        if (Boolean.TRUE.equals(vistaAlMar)) precio += 80000;
        return precio;
    }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public Boolean getIncluyeDesayuno() { return incluyeDesayuno; }
    public void setIncluyeDesayuno(Boolean incluyeDesayuno) { this.incluyeDesayuno = incluyeDesayuno; }

    public Boolean getVistaAlMar() { return vistaAlMar; }
    public void setVistaAlMar(Boolean vistaAlMar) { this.vistaAlMar = vistaAlMar; }
}
