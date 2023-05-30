import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "cola3")
    public void processPedido(String usuarioJson) {
        try {
            // Convertir el JSON recibido a un objeto Pedido
            Usuario usuario = objectMapper.readValue(usuarioJson, Usuario.class);
            // Procesar el pedido
            System.out.println("Pedido recibido: " + usuario);
            // Aquí puedes agregar la lógica para procesar el pedido de acuerdo a tus necesidades
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
