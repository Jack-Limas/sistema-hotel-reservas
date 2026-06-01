package com.hotel.reservas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// RECURSIVIDAD: Esta clase se referencia a sí misma a través de los atributos 'padre' e 'hijos',
// permitiendo construir estructuras jerárquicas de menú de n niveles de profundidad.
@Entity
@Table(name = "opcion_menu")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OpcionMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ruta;
    private String icono;
    private Integer orden;
    private Boolean activo = true;
    private String rol;

    // RECURSIVIDAD — esta clase se referencia a sí misma
    // padre: apunta al OpcionMenu que lo contiene (null si es raíz)
    // hijos: lista de OpcionMenu que este nodo contiene
    // Estructura de árbol: Sistema Hotel → Gestión → Huéspedes
    //                                              → Habitaciones
    //                      Sistema Hotel → Reportes → Facturas
    @JsonIgnoreProperties({"hijos", "padre", "hibernateLazyInitializer"})
    @ManyToOne
    @JoinColumn(name = "padre_id", nullable = true)
    private OpcionMenu padre;

    // RECURSIVIDAD: Lista de nodos hijos. @JsonIgnoreProperties corta la referencia al padre
    // en cada hijo, evitando recursión infinita en la serialización JSON.
    @JsonIgnoreProperties({"padre", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "padre")
    private List<OpcionMenu> hijos = new ArrayList<>();

    public OpcionMenu() {}

    public OpcionMenu(String nombre, String ruta, String icono,
                      Integer orden, String rol, OpcionMenu padre) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.icono = icono;
        this.orden = orden;
        this.rol = rol;
        this.padre = padre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public OpcionMenu getPadre() { return padre; }
    public void setPadre(OpcionMenu padre) { this.padre = padre; }

    public List<OpcionMenu> getHijos() { return hijos; }
    public void setHijos(List<OpcionMenu> hijos) { this.hijos = hijos; }
}
