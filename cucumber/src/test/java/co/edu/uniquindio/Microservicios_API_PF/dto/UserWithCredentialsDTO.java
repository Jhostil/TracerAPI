package co.edu.uniquindio.Microservicios_API_PF.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
@Builder
@RequiredArgsConstructor(onConstructor_= {@ConstructorProperties({"id","correo","nombre","apellido", "credentialDTO"})})
public class UserWithCredentialsDTO {

    private final String id;

    private final String correo;

    private final String nombre;

    private final String apellido;

    private final CredentialDTO credential;
}
