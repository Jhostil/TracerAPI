package co.edu.uniquindio.Microservicios_API_PF.repositorios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepo extends JpaRepository<Pedido, String> {
    Optional<Pedido> findById(String id_pedido) ;

}
