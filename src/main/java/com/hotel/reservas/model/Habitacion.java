package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.TipoHabitacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

// ABSTRACCION: Define la estructura y el contrato base para todos los tipos de habitación,
// forzando a cada subclase a implementar su propio cálculo de precio.
// HERENCIA: Superclase de HabitacionEstandar, HabitacionFamiliar, HabitacionSuite y HabitacionEjecutiva.
@Entity
@Table(name = "habitacion")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHabitacion;

    @NotBlank
    private String numero;

    private Integer piso;
    private Integer capacidad;
    private String descripcion;
    private Boolean disponible;

    @Enumerated(EnumType.STRING)
    private TipoHabitacion tipo;

    // ABSTRACCION: Método abstracto que obliga a cada subclase a definir su propia lógica de precio.
    public abstract Double calcularPrecio();

    public Habitacion() {}

    public Habitacion(String numero, Integer piso, Integer capacidad,
                      String descripcion, Boolean disponible, TipoHabitacion tipo) {
        this.numero = numero;
        this.piso = piso;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.tipo = tipo;
    }

    public Long getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(Long idHabitacion) { this.idHabitacion = idHabitacion; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Integer getPiso() { return piso; }
    public void setPiso(Integer piso) { this.piso = piso; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public TipoHabitacion getTipo() { return tipo; }
    public void setTipo(TipoHabitacion tipo) { this.tipo = tipo; }
}
