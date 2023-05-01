package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;

public interface TokenServicio {
    String generateToken(String subject);
    boolean validateToken(String token, Usuario usuario);
    String getSubjectFromToken(String token);
}
