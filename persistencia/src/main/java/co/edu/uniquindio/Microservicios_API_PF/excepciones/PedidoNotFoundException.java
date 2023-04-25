package co.edu.uniquindio.Microservicios_API_PF.excepciones;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String message) {
        super(message);
    }
}

