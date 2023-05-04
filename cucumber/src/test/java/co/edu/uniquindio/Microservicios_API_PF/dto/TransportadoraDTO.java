package co.edu.uniquindio.Microservicios_API_PF.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor_={@ConstructorProperties({"nombre", "direccion", "telefono"})} )
public class TransportadoraDTO implements Serializable {

    private final String nombre;

    private final String direccion;

    private final String telefono;

}

