package co.edu.uniquindio.Microservicios_API_PF.excepciones;

public class UsuarioRegistradoException extends RuntimeException {
    public UsuarioRegistradoException(String message) {
        super(message);
    }
}
