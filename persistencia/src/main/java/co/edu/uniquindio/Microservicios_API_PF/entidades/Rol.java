package co.edu.uniquindio.Microservicios_API_PF.entidades;

import java.io.Serializable;

public enum Rol implements Serializable {
    empleado("Empleado", "Rol de empleado"),
    administrador("Administrador", "Rol de administrador"),

    cliente("cliente", "Rol de cliente");

    private String nombre;
    private String descripcion;

    private Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String toString() {
        return nombre;
    }
}