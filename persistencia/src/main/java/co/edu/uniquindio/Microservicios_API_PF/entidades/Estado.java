package co.edu.uniquindio.Microservicios_API_PF.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Estado implements Serializable {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("detalle")
    private String detalle;

    @JsonProperty("descripcion")
    private Descripcion descripcion;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
