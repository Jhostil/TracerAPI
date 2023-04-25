package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;

import java.util.Optional;

public interface PedidoServicio {

    Optional<Pedido> findById_pedido(String id_pedido) ;

    void save (Pedido pedido);
}
