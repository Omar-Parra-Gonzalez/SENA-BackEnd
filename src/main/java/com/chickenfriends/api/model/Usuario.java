package com.chickenfriends.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long id;

    @Column(name = "nombres_y_apellidos")
    private String nombresYApellidos;
    private String telefono;
    private String correo;
    private String codigo;
    private String cedula;
    private String contrasena;

    public long getId() {
        return id;
    }

    public String getNombresYApellidos() {
        return nombresYApellidos;
    }

    public void setNombresYApellidos(String nombresYApellidos) {
        this.nombresYApellidos = nombresYApellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
