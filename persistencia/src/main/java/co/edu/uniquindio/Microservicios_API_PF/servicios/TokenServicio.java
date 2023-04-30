package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Token;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.TokenRepo;

import java.util.Date;
import java.util.Optional;

public interface TokenServicio {
    String generateToken(String subject);
    boolean validateToken(String token);
    String getSubjectFromToken(String token);
}
