package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Estado;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.EstadoRepo;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.PedidoRepo;
import org.springframework.stereotype.Service;

import javax.persistence.TransactionRequiredException;
import java.util.Optional;

@Service
public class PedidoServicioImpl implements PedidoServicio{

    private final PedidoRepo pedidoRepo;

    private final EstadoRepo estadoRepo;

    public PedidoServicioImpl (PedidoRepo pedidoRepo, EstadoRepo estadoRepo)
    {
        this.pedidoRepo = pedidoRepo;
        this.estadoRepo = estadoRepo;
    }
    public Optional<Pedido> findById_pedido(String id_pedido) {
        /*Pedido p = null;
        for (Pedido pedido : pedidos) {

            if (pedido.getId_pedido().equals(id_pedido)) {
                p = pedido;
            }
        }*/
        Optional pd = pedidoRepo.findById(id_pedido);

        return pd;
    }

    public void save(Pedido pedido) {

        //pedidos.add(pedido);
        try {
            pedidoRepo.save(pedido); // intentamos guardar el pedido en la base de datos
        } catch (TransactionRequiredException e) {
            // si ocurre una excepción de tipo TransactionRequiredException, la relanzamos como RuntimeException
            throw new RuntimeException(e);
        }
    }

    public void saveEstado(Estado estado) {
        System.out.println("Llegue");
        //pedidos.add(pedido);
        try {
            estadoRepo.save(estado); // intentamos guardar el pedido en la base de datos
            System.out.println("Sali");
        } catch (TransactionRequiredException e) {
            // si ocurre una excepción de tipo TransactionRequiredException, la relanzamos como RuntimeException
            throw new RuntimeException(e);
        }
    }
}
