package co.edu.uniquindio.Microservicios_API_PF.entidades;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Pedido implements Serializable {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("estado")
    @OneToMany(mappedBy = "pedido")
    private List<Estado> estado;

    @JsonProperty("fecha_envio")
    //private LocalDateTime fecha_envio;
    private String fecha_envio;

    @JsonProperty("fecha_entrega")
    //private LocalDateTime fecha_entrega;
    private String fecha_entrega;

    @JsonProperty("ubicaciones")
    @OneToMany(mappedBy = "pedido")
    private List<Ubicacion> ubicaciones;

    public Pedido(){}


}
