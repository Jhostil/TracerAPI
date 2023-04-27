package co.edu.uniquindio.Microservicios_API_PF.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor_={@ConstructorProperties({"id_pedido", "detalle", "descripcion"})} )
public class EstadoDTO implements Serializable {
    private final String id_pedido;
    private final String detalle;
    private final DescripcionDTO descripcion;
}
