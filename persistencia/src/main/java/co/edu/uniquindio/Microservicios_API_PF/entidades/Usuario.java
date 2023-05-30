package co.edu.uniquindio.Microservicios_API_PF.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

    @Id
    @JsonProperty("id")
    private String id;

    @Column(unique = true)
    @JsonProperty("correo")
    private String correo;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apellido")
    private String apellido;

    @OneToOne
    @JsonProperty("credential")
    private Credential credential;
}
