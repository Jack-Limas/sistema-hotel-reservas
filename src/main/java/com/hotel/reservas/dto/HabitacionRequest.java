package com.hotel.reservas.dto;

public class HabitacionRequest {

    private String numero;
    private Integer piso;
    private Integer capacidad;
    private String descripcion;
    private Boolean disponible;
    private String tipo;
    private Double precioBase;
    private Boolean incluyeDesayuno;
    private Boolean vistaAlMar;
    private Boolean pisoEjecutivo;
    private Integer numeroCamas;

    public HabitacionRequest() {}

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

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public Boolean getIncluyeDesayuno() { return incluyeDesayuno; }
    public void setIncluyeDesayuno(Boolean incluyeDesayuno) { this.incluyeDesayuno = incluyeDesayuno; }

    public Boolean getVistaAlMar() { return vistaAlMar; }
    public void setVistaAlMar(Boolean vistaAlMar) { this.vistaAlMar = vistaAlMar; }

    public Boolean getPisoEjecutivo() { return pisoEjecutivo; }
    public void setPisoEjecutivo(Boolean pisoEjecutivo) { this.pisoEjecutivo = pisoEjecutivo; }

    public Integer getNumeroCamas() { return numeroCamas; }
    public void setNumeroCamas(Integer numeroCamas) { this.numeroCamas = numeroCamas; }
}
