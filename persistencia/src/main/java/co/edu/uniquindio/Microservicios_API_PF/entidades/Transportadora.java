package co.edu.uniquindio.Microservicios_API_PF.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Transportadora implements Serializable {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("direccion")
    private String direccion;

    @JsonProperty("telefono")
    private String telefono;

    @ToString.Exclude
    @JsonProperty("pedidos")
    @JsonIgnoreProperties("transportadora")
    @OneToMany(mappedBy = "transportadora")
    private List<Pedido> pedidos;

}
