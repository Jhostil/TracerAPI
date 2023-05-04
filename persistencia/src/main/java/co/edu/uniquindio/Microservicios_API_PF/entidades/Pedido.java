package co.edu.uniquindio.Microservicios_API_PF.entidades;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Pedido implements Serializable {

    @Id
    @JsonProperty("id")
    private String id;

    @ToString.Exclude
    @JsonProperty("estado")
    @OneToMany(mappedBy = "pedido")
    private List<Estado> estado;

    @JsonProperty("fecha_envio")
    private String fecha_envio;

    @JsonProperty("fecha_entrega")
    private String fecha_entrega;

    @ToString.Exclude
    @JsonProperty("ubicaciones")
    @JsonIgnoreProperties("pedido")
    @OneToMany(mappedBy = "pedido")
    private List<Ubicacion> ubicaciones;

    @ToString.Exclude
    @JsonIgnoreProperties("pedidos")
    @JsonProperty("transportadora")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transportadora_id")
    private Transportadora transportadora;


    public Pedido(){}



}
