package com.hotel.reservas.dto;

import com.hotel.reservas.model.enums.Rol;
import com.hotel.reservas.model.enums.TipoDocumento;

public class RegisterRequest {

    private String username;
    private String password;
    private Rol rol;
    private String nombre;
    private String correo;
    private String telefono;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, Rol rol, String nombre,
                            String correo, String telefono,
                            TipoDocumento tipoDocumento, String numeroDocumento) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

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
}
