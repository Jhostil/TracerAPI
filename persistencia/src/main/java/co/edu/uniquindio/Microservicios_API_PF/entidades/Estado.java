package co.edu.uniquindio.Microservicios_API_PF.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private String id;

    @JsonProperty("detalle")
    private String detalle;

    @JsonProperty("descripcion")
    private Descripcion descripcion;

    @ManyToOne
    @JoinColumn(name = "pedido_id_pedido")
    private Pedido pedido;
}
