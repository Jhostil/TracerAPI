package co.edu.uniquindio.Microservicios_API_PF.servicios;

public interface TokenServicio {
    String generateToken(String subject);
    boolean validateToken(String token);
    String getSubjectFromToken(String token);
}
