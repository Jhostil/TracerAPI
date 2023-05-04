package co.edu.uniquindio.Microservicios_API_PF.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor_= {@ConstructorProperties({"id","username", "password", "rol"})})
public class CredentialDTO {

    private final String id;

    private final String username;

    private final String password;

    private final RolDTO rol;

}
