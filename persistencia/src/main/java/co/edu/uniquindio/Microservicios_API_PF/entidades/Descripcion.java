package co.edu.uniquindio.Microservicios_API_PF.entidades;

import java.io.Serializable;

public enum Descripcion implements Serializable {
    EN_RUTA(0),
    ENTREGADO(1),
    EN_BODEGA(2),
    EN_PREPARACION(3);

    private int descripcion;

    private Descripcion(int descripcion) {
        this.descripcion = descripcion;
    }

    public int getDescripcion() {
        return descripcion;
    }

    public String toString() {
        String tipoDescripcion = " ";
        switch (descripcion) {
            case 0:
                tipoDescripcion = "EN_RUTA";
                break;
            case 1:
                tipoDescripcion = "ENTREGADO";
                break;
            case 2:
                tipoDescripcion = "EN_BODEGA";
                break;
            case 3:
                tipoDescripcion = "EN_PREPARACION";
                break;
        }
        return tipoDescripcion;
    }
}
