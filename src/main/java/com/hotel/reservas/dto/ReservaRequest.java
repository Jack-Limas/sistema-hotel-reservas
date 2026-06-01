package com.hotel.reservas.dto;

public class ReservaRequest {

    private String fechaInicio;
    private String fechaFin;
    private Long idHuesped;
    private Long idHabitacion;

    public ReservaRequest() {}

    public ReservaRequest(String fechaInicio, String fechaFin,
                           Long idHuesped, Long idHabitacion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idHuesped = idHuesped;
        this.idHabitacion = idHabitacion;
    }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public Long getIdHuesped() { return idHuesped; }
    public void setIdHuesped(Long idHuesped) { this.idHuesped = idHuesped; }

    public Long getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(Long idHabitacion) { this.idHabitacion = idHabitacion; }
}
