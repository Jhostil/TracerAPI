package co.edu.uniquindio.Microservicios_API_PF.entidades;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("token_string")
    private String tokenString;

    @JsonProperty("expiration_date")
    private LocalDateTime expirationDate;

}
