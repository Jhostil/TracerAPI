package co.edu.uniquindio.Microservicios_API_PF.entidades;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Ubicacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonProperty("latitud")
    private double latitud;
    @JsonProperty("longitud")
    private double longitud;

    @ManyToOne
    @JoinColumn(name = "pedido_id_pedido")
    private Pedido pedido;
}