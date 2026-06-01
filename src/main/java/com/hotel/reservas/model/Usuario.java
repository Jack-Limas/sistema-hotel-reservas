package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.reservas.model.enums.Rol;
import jakarta.persistence.*;

// ENCAPSULAMIENTO: Los datos sensibles del usuario (password, rol, estado) están protegidos
// por atributos privados con acceso controlado mediante getters y setters.
@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(unique = true)
    private String username;

    private String password;

    private String estado = "ACTIVO";

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_huesped", nullable = true)
    private Huesped huesped;

    public Usuario() {}

    public Usuario(String username, String password, Rol rol, Huesped huesped) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.huesped = huesped;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Huesped getHuesped() { return huesped; }
    public void setHuesped(Huesped huesped) { this.huesped = huesped; }
}
