package co.edu.uniquindio.Microservicios_API_PF.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor_={@ConstructorProperties({"latitud", "longitud"})} )
public class UbicacionDTO implements Serializable {

    private final double latitud;

    private final double longitud;
}
