package co.edu.uniquindio.Microservicios_API_PF.entidades;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

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

    @ToString.Exclude
    @JsonIgnoreProperties("ubicaciones")
    @JsonProperty("pedido")
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}