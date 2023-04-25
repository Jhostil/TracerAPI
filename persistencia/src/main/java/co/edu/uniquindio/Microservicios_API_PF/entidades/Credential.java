package co.edu.uniquindio.Microservicios_API_PF.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
public class Credential implements Serializable {

    @JsonProperty("usuario")
    @Id
    private String username;

    @JsonProperty("clave")
    @NotBlank(message = "La clave es obligatoria.")
    private String password;
}
