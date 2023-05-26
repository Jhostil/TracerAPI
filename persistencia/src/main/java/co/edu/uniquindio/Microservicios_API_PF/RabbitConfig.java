package co.edu.uniquindio.Microservicios_API_PF;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;


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
}