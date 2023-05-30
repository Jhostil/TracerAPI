package co.edu.uniquindio.Microservicios_API_PF;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;
import co.edu.uniquindio.Microservicios_API_PF.servicios.UsuarioServicio;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class RabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UsuarioServicio usuarioServicio;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue usr_create() {
        return new Queue("usr_create"); // Simplemente declara la cola "usr_create"
    }

    @Bean
    public Queue usr_delete() {
        return new Queue("usr_delete", true);
    }
    @Bean
    public Queue usr_update() {
        return new Queue("usr_update", true);
    }

    @Bean
    public Queue myQueue() {
        return new Queue("cola3");
    }


    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("cola3", message);
    }

    public void sendPedido(Pedido pedido) {
        try {
            String pedidoJson = objectMapper.writeValueAsString(pedido);
            rabbitTemplate.convertAndSend("cola3", pedidoJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void processUserCreatedMessage(String message) throws JsonProcessingException {
        System.out.println("Mensaje de usuario nuevo recibido.");
        Usuario usuario = objectMapper.readValue(message, Usuario.class);
        Usuario nuevoUsuario = usuarioServicio.crearUsuario(usuario);
        System.out.println("Usuario creado con éxito");
    }

    public void processUserUpdateMessage(String message) throws JsonProcessingException {
        System.out.println("Mensaje de usuario actualizado recibido.");
        Usuario usuario = objectMapper.readValue(message, Usuario.class);
        Usuario actualizadoUsuario = usuarioServicio.actualizarUsuario(usuario);
        System.out.println("Usuario actualizado con éxito");
    }

    public void processUserDeleteMessage(String message) throws JsonProcessingException {
        System.out.println("Mensaje de usuario eliminado recibido.");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message);
        String idUsuario = jsonNode.get("user_id").asText();
        usuarioServicio.eliminarUsuario(idUsuario);
        System.out.println("Usuario eliminado con éxito");
    }

    public void receiveUserCreatedMessage(String message) throws JsonProcessingException {
        processUserCreatedMessage(message);
    }

    public void receiveUserUpdatedMessage(String message) throws JsonProcessingException {
        processUserUpdateMessage(message);
    }

    public void receiveUserDeletedMessage(String message) throws JsonProcessingException {
        processUserDeleteMessage(message);
    }

    @RabbitListener(queues = "usr_create")
    public void listenerUserCreate(String message) throws JsonProcessingException {
        receiveUserCreatedMessage(message);
    }

    @RabbitListener(queues = "usr_update")
    public void listenerUserUpdate(String message) throws JsonProcessingException {
        receiveUserUpdatedMessage(message);
    }

    @RabbitListener(queues = "usr_delete")
    public void listenerUserDelete(String message) throws JsonProcessingException {
        receiveUserDeletedMessage(message);
    }
}