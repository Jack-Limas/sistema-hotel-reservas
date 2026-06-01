package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// ENCAPSULAMIENTO: Todos los atributos del huésped son privados y se acceden únicamente
// a través de getters y setters, protegiendo la integridad de los datos personales.
@Entity
@Table(name = "huesped")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHuesped;

    @NotBlank
    private String nombre;

    @Email
    @Column(unique = true)
    private String correo;

    private String telefono;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(unique = true)
    private String numeroDocumento;

    private Boolean activo = true;

    public Huesped() {}

    public Huesped(String nombre, String correo, String telefono,
                   TipoDocumento tipoDocumento, String numeroDocumento) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public Long getIdHuesped() { return idHuesped; }
    public void setIdHuesped(Long idHuesped) { this.idHuesped = idHuesped; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDocumento tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
