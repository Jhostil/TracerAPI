package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Credential;

import java.util.Optional;

public interface CredentialServicio {
    Credential crearCredential(Credential credential);
    Credential actualizarCredential(Credential credential) throws Exception;
    void eliminarCredential(String id) throws Exception;
    Optional<Credential> obtenerCredentialPorId(String id);
    Optional<Credential> obtenerCredentialPorUsername(String username);
    boolean validarCredential(String username, String password);
}
