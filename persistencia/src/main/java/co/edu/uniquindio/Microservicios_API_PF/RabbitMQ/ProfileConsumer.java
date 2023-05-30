package co.edu.uniquindio.Microservicios_API_PF.RabbitMQ;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;
import co.edu.uniquindio.Microservicios_API_PF.servicios.UsuarioServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProfileConsumer {

    private final UsuarioServicio usuarioServicio;
    private final ObjectMapper objectMapper;

    public ProfileConsumer(UsuarioServicio usuarioServicio, ObjectMapper objectMapper) {
        this.usuarioServicio = usuarioServicio;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "nuevo_usuario")
    public void recibirNuevoUsuario(String mensaje) {
        try {
            Usuario nuevoUsuario = convertirMensajeAUsuario(mensaje);
            usuarioServicio.crearUsuario(nuevoUsuario);
        } catch (Exception e) {
            // Manejo de errores al procesar el mensaje
        }
    }

    private Usuario convertirMensajeAUsuario(String mensaje) throws Exception {
        try {
            return objectMapper.readValue(mensaje, Usuario.class);
        } catch (Exception e) {
            throw new Exception("Error al convertir el mensaje JSON a objeto Usuario");
        }
    }
}
