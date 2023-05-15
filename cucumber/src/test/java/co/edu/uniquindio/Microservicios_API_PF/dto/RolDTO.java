package co.edu.uniquindio.Microservicios_API_PF.dto;

import lombok.Getter;
import java.io.Serializable;

@Getter
public enum RolDTO implements Serializable {
    EMPLEADO("Empleado", "Rol de empleado"),
    ADMINISTRADOR("Administrador", "Rol de administrador"),
    USUARIO("Usuario", "Rol de usuario");

    private String nombre;
    private String descripcion;

    private RolDTO(String nombre, String descripcion) {
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
