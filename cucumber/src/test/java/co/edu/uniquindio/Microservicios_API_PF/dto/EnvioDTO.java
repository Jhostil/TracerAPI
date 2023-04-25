package co.edu.uniquindio.Microservicios_API_PF.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor_={@ConstructorProperties({"id_pedido", "estado", "fecha_envio", "fecha_entrega"})} )
public class EnvioDTO implements Serializable {

    private final String id;

    private final String estado;

    //   private final LocalDateTime fecha_envio;
    private final String fecha_envio;

    // private final LocalDateTime fecha_entrega;

    private final String fecha_entrega;

    private final List<UbicacionDTO> ubicaciones;

}
